package com.a05.simaya.keuangan.controller;

import com.a05.simaya.anggota.payload.AnggotaDTO;
import com.a05.simaya.keuangan.payload.KeuanganDTO;
import com.a05.simaya.keuangan.service.KeuanganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class KeuanganController {

    @Autowired
    private KeuanganService keuanganService;

    @GetMapping(value = "/keuangan")
    public String ringkasanKeuangan() {
        return "keuangan/daftar-keuangan";
    }

    @GetMapping(value = "/tambah-pemasukan")
    public String getTambahPemasukan(Model model) {
        KeuanganDTO keuangan = new KeuanganDTO();

        model.addAttribute("keuangan", keuangan);
        return "keuangan/form-tambah-pemasukan";
    }

    @PostMapping(value = "/tambah-pemasukan")
    public String postFormPemasukan(KeuanganDTO keuanganDTO, RedirectAttributes redirectAttributes){
        keuanganService.tambahPemasukan(keuanganDTO);
        redirectAttributes.addFlashAttribute("success", "Data pemasukan berhasil ditambahkan!");

        // TODO: Menyesuaikan redirect ke halaman ringkasan pemasukan & pengeluaran
        return "redirect:/keuangan";
    }

    @GetMapping(value = "/tambah-pengeluaran")
    public String getTambahPengeluaran(Model model) {
        KeuanganDTO keuangan = new KeuanganDTO();

        model.addAttribute("keuangan", keuangan);
        return "keuangan/form-tambah-pengeluaran";
    }

    @PostMapping(value = "/tambah-pengeluaran")
    public String postFormPengeluaran(KeuanganDTO keuanganDTO, RedirectAttributes redirectAttributes){
        keuanganService.tambahPengeluaran(keuanganDTO);
        redirectAttributes.addFlashAttribute("success", "Data pengeluaran berhasil ditambahkan!");

        return "redirect:/keuangan";
    }
}
