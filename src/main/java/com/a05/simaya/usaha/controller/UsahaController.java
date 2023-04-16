package com.a05.simaya.usaha.controller;

import com.a05.simaya.anggota.service.AnggotaService;
import com.a05.simaya.usaha.model.GambarUsahaModel;
import com.a05.simaya.usaha.model.StatusUsaha;
import com.a05.simaya.usaha.model.UsahaModel;
import com.a05.simaya.usaha.payload.UsahaDTO;
import com.a05.simaya.usaha.service.UsahaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class UsahaController {
    @Autowired
    private UsahaService usahaService;

    @Autowired
    private AnggotaService anggotaService;

    @GetMapping(value = "/tambah-usaha")
    public String formTambahUsaha(Model model, Principal principal) {
        model.addAttribute("usaha", usahaService.getDataPenjual(principal.getName()));
        return "usaha/tambah-usaha";
    }

    @PostMapping(value = "/tambah-usaha")
    public String tambahUsaha(UsahaDTO usahaDTO,
                              @RequestParam("upload") MultipartFile[] images,
                              RedirectAttributes redirectAttributes,
                              Principal principal) throws IOException {
        usahaDTO.setUsername(principal.getName());
        UsahaModel usahaModel = usahaService.tambahUsaha(usahaDTO);

        List<GambarUsahaModel> files = usahaService.uploadPhoto(images, usahaModel);

        if (files != null) {
            usahaService.simpanPhoto(usahaModel, files);
        }

        String currentDate = usahaModel.getLastEdit().plusMinutes(10).format(DateTimeFormatter.ofPattern("HH:mm"));

        redirectAttributes.addFlashAttribute("success", String.format("Data usaha %s berhasil ditambahkan. " +
                        "Anda memiliki waktu 10 menit untuk mengubah atau menghapus data usaha sampai pukul %s.",
                usahaModel.getNamaProduk(), currentDate));

        return "redirect:/daftar-usaha-saya";
    }

    @GetMapping(value = "/ubah-usaha/{id}")
    public String formUbahUsaha(Model model, @PathVariable("id") String id) {
        UsahaModel usahaModel = usahaService.getUsaha(id);
        Duration duration = Duration.between(usahaModel.getLastEdit(), LocalDateTime.now());

        if ((usahaModel.getStatusUsaha() == StatusUsaha.BELUM_TERVERIFIKASI) && (duration.getSeconds()/60 > 10)) {
            return "redirect:/detail-usaha/" + id;
        }

        model.addAttribute("usaha", usahaService.getDataUsaha(id));
        return "usaha/ubah-usaha";
    }

    @PostMapping(value = "/ubah-usaha")
    public String ubahUsaha(UsahaDTO usahaDTO,
                            @RequestParam("upload") MultipartFile[] images,
                            RedirectAttributes redirectAttributes, Principal principal) throws IOException {
        usahaDTO.setUsername(principal.getName());
        UsahaModel usahaModel = usahaService.ubahUsaha(usahaDTO);

        List<GambarUsahaModel> files = usahaService.uploadPhoto(images, usahaModel);

        if (files != null) {
            usahaService.simpanPhoto(usahaModel, files);
        }

        String currentDate = usahaModel.getLastEdit().plusMinutes(10).format(DateTimeFormatter.ofPattern("HH:mm"));

        redirectAttributes.addFlashAttribute("success", String.format("Data usaha %s berhasil diubah. " +
                        "Anda memiliki waktu untuk mengubah atau menghapus data usaha sampai pukul %s.",
                usahaModel.getNamaProduk(), currentDate));

        return "redirect:/detail-usaha/" + usahaDTO.getIdUsaha();
    }

    @GetMapping(value = "/detail-usaha/{id}")
    public String detailUsaha(Principal principal, Model model, @PathVariable("id") String id) {
        UsahaModel usaha = usahaService.getUsaha(id);
        Duration duration = Duration.between(usaha.getLastEdit(), LocalDateTime.now());

        List<GambarUsahaModel> listGambar = usaha.getGambar();

        model.addAttribute("anggota", anggotaService.getAnggotaByUsername(principal.getName()));
        model.addAttribute("usaha", usaha);
        model.addAttribute("waktu", duration.getSeconds()/60);
        model.addAttribute("listGambar", listGambar);

        return "usaha/detail-usaha";
    }

    @GetMapping(value = "/hapus-usaha/{id}")
    public String hapusUsaha(RedirectAttributes redirectAttributes,
                             @PathVariable("id") String id) {
        UsahaModel usaha = usahaService.getUsaha(id);
        Boolean result = usahaService.hapusUsaha(id);;

        if (result){
            redirectAttributes.addFlashAttribute("success", String.format("Produk bernama %s berhasil dihapus!", usaha.getNamaProduk()));
        } else {
            redirectAttributes.addFlashAttribute("error", String.format("Produk dengan id %s gagal dihapus! karena id tersebut tidak ditemukan", id));
        }
        return "redirect:/daftar-usaha-saya";
    }

    @GetMapping(value = "/daftar-usaha/{page}")
    public String daftarUsaha(Model model, @PathVariable("page") int page) {
        List<UsahaModel> listUsaha = usahaService.getUsahaByStatus(StatusUsaha.TERVERIFIKASI);
        Integer seq = 4; // Sequential 4
        List<UsahaModel> listUsahaPage = assignPagination(listUsaha, page, seq);
        String search = "";

        model.addAttribute("listUsaha", listUsahaPage);
        model.addAttribute("totalPage",  (Math.ceil(listUsaha.size()/ seq.floatValue())) -1);
        model.addAttribute("page", page);
        model.addAttribute("search", search);
        return "usaha/daftar-usaha";
    }

    @GetMapping(value = "/daftar-usaha/search/{page}")
    public String daftarUsahaSearch(Model model, @RequestParam("search") String name, @PathVariable("page") int page){
        List<UsahaModel> listUsaha = usahaService.getListUsahaByName(name);
        Integer seq = 4; // Sequential 4
        List<UsahaModel> listUsahaPage = assignPagination(listUsaha, page, seq);

        model.addAttribute("listUsaha", listUsahaPage);
        model.addAttribute("totalPage",  (Math.ceil(listUsaha.size()/ seq.floatValue())) -1);
        model.addAttribute("page", page);
        model.addAttribute("search", name);
        return "usaha/daftar-usaha";
    }

    private List<UsahaModel> assignPagination(List<UsahaModel> listUsaha, int page, int seq){
        List<UsahaModel> listUsahaPage = new ArrayList<>();

        for (int i = page*seq - seq ; i < seq*page ; i++ ) {
            if (listUsaha.size() == i){
                break;
            }
            listUsahaPage.add(listUsaha.get(i));
        }
        return listUsahaPage;
    }

    @GetMapping(value = "/daftar-usaha-verifikasi")
    public String getDaftarUsahaYangPerluDiverifikasi() {
        return "usaha/daftar-usaha-perlu-diverifikasi";
    }

    @GetMapping(value = "/daftar-usaha-saya")
    public String getDaftarUsahaSaya(Model model,
                                     Principal principal) {
        List<UsahaModel> listUsaha = usahaService.getUsahaByUsername(principal.getName());
        model.addAttribute("listUsaha", listUsaha);
        return "usaha/daftar-usaha-saya";
    }

}
