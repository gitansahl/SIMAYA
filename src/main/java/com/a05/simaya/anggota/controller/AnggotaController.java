package com.a05.simaya.anggota.controller;

import com.a05.simaya.anggota.model.AnggotaModel;
import com.a05.simaya.anggota.model.ProfileModel;
import com.a05.simaya.anggota.payload.AnggotaDTO;
import com.a05.simaya.anggota.payload.UbahPasswordDTO;
import com.a05.simaya.anggota.repository.AnggotaDb;
import com.a05.simaya.anggota.service.AnggotaService;
import com.a05.simaya.event.model.DirektoratEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;

@Controller
@Slf4j
public class AnggotaController {

    @Autowired
    private AnggotaService anggotaService;

    @GetMapping(value = "/tambah-anggota")
    public String getTambahAnggotaPage(Model model) {
        AnggotaDTO anggota = new AnggotaDTO();

        model.addAttribute("anggota", anggota);
        return "anggota/form-tambah-anggota";
    }

    @PostMapping(value = "/anggota/viewall")
    public String postForm(AnggotaDTO anggota, ModelMap model) {
        anggotaService.tambahAnggota(anggota);

        String info = "Data anggota dengan nama " + anggota.getNamaDepan() + " " +
                anggota.getNamaBelakang() + " telah berhasil ditambahkan";
        model.addAttribute("modal_add", info);

        return "anggota/daftar-anggota";
    }

    @GetMapping(value = "/ubah-profil")
    public String getUbahProfilePage(Principal principal,
                                     Model model) {
        AnggotaModel anggota = anggotaService.getAnggotaByUsername(principal.getName());
        AnggotaDTO updateAnggotaDTO = anggotaService.getInfoAnggota(anggota.getId());

        model.addAttribute("updateAnggota", updateAnggotaDTO);
        return "anggota/form-ubah-profile";
    }

    @PostMapping(value = "/ubah-profil")
    public String ubahProfile(AnggotaDTO updateAnggota,
                              Principal principal,
                              @RequestParam("upload") MultipartFile image) throws IOException {

        String fileName = anggotaService.uploadProfile(image, principal.getName(), updateAnggota.getProfile().getPhotoUrl());
        log.info(updateAnggota.getProfile().getPhotoUrl());
        updateAnggota.getProfile().setPhotoUrl(fileName);
        anggotaService.updateDataAnggota(updateAnggota);
        return "redirect:/profil";
    }

    @GetMapping(value = "/ubah-data-anggota/{id}")
    public String getUbahDataAnggotaPage(@PathVariable String id, Model model) {
        AnggotaDTO updateAnggotaDTO = anggotaService.getInfoAnggota(id);

        model.addAttribute("updateAnggota", updateAnggotaDTO);
        return "anggota/form-ubah-data-anggota";
    }

    @PostMapping(value = "/ubah-data-anggota")
    public String  ubahDataAnggota(AnggotaDTO updateAnggota) {
        anggotaService.updateDataAnggota(updateAnggota);
        return "redirect:/anggota/viewall";
    }

    @GetMapping(value = "/anggota/viewall")
    public String viewAllAnggota() {
        return "anggota/daftar-anggota";
    }

    @GetMapping(value = "/profil")
    public String profilPage(Model model,
                             Principal principal) {
        AnggotaModel anggota = anggotaService.getAnggotaByUsername(principal.getName());
        UbahPasswordDTO ubahPasswordDTO = new UbahPasswordDTO();
        ubahPasswordDTO.setId(anggota.getId());

        model.addAttribute("anggota", anggota);
        model.addAttribute("aset", getAset(anggota.getProfile()));
        model.addAttribute("divisi", getDivisi(anggota.getProfile().getDivisi()));
        model.addAttribute("ubahPassword", ubahPasswordDTO);

        return "anggota/profile";
    }

    @PostMapping(value = "/profil")
    public String ubahPassword(Model model, UbahPasswordDTO ubahPasswordDTO, Principal principal) {
        AnggotaModel anggota = anggotaService.getAnggotaByUsername(principal.getName());

        if (anggotaService.cekPassword(ubahPasswordDTO.getId(), ubahPasswordDTO.getNewPassword())) {
            model.addAttribute("wrong_password", "Masukkan kata sandi yang berbeda!");
        }
        else if (!anggotaService.cekPassword(ubahPasswordDTO.getId(), ubahPasswordDTO.getOldPassword())) {
            model.addAttribute("wrong_password", "Kata sandi lama yang dimasukkan salah!");
        } else {
            anggotaService.gantiPassword(ubahPasswordDTO.getId(), ubahPasswordDTO.getNewPassword());
        }

        model.addAttribute("anggota", anggota);
        model.addAttribute("aset", getAset(anggota.getProfile()));
        model.addAttribute("divisi", getDivisi(anggota.getProfile().getDivisi()));
        model.addAttribute("ubahPassword", ubahPasswordDTO);

        return "anggota/profile";
    }

    @GetMapping(value = "/profil/{id}")
    public String profilAnggotaPage(@PathVariable(value = "id") String id,
                                    Model model, Authentication authentication) {
        AnggotaModel anggota = anggotaService.getAnggotaById(id);
        String role = "";
        if( authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")) ){
            role = "Admin";
        }else{
            role = "Anggota";
        }
        model.addAttribute("role",role);
        model.addAttribute("anggota", anggota);
        model.addAttribute("aset", getAset(anggota.getProfile()));
        model.addAttribute("divisi", getDivisi(anggota.getProfile().getDivisi()));

        return "anggota/detail-anggota";
    }

    public String getAset(ProfileModel profile) {
        String res = "-";

        if (profile.getIsPunyaMobil().equals(Boolean.TRUE)) {
            res = "Mobil";
        }

        if (profile.getIsPunyaMotor().equals(Boolean.TRUE)) {
            res = res.equals("-") ? "Motor" : res + ", Motor";
        }

        if (profile.getIsPunyaRumah().equals(Boolean.TRUE)) {
            res = res.equals("-") ? "Rumah" : res + ", Rumah";
        }

        if (profile.getIsPunyaVila().equals(Boolean.TRUE)) {
            res = res.equals("-") ? "Vila" : res + ", Vila";
        }
        return res;
    }

    public String getDivisi(DirektoratEnum direktorat) {
        if (direktorat == null) return "Unknown";
        if (direktorat.equals(DirektoratEnum.KEUANGAN_PROGRAM)) return "Keuangan dan Program";
        if (direktorat.equals(DirektoratEnum.SDM_OPERASIONAL)) return "SDM dan Operasional";
        else return "Pengembangan Usaha";
    }
}
