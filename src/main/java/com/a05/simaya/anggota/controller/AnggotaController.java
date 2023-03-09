package com.a05.simaya.anggota.controller;


import com.a05.simaya.anggota.payload.CreateAnggotaDTO;
import com.a05.simaya.anggota.service.AnggotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AnggotaController {

    @Autowired
    private AnggotaService anggotaService;

    @GetMapping(value = "/tambah-anggota")
    public String getPage(Model model) {
        CreateAnggotaDTO anggota = new CreateAnggotaDTO();

        model.addAttribute("anggota", anggota);
        return "anggota/form-tambah-anggota";
    }

    @PostMapping(value = "/tambah-anggota")
    public String postForm(CreateAnggotaDTO anggota) {
        anggotaService.tambahAnggota(anggota);
        return "hello_world";
    }
    @GetMapping(value = "/anggota/viewall")
    public String viewAllAnggota() {
        return "anggota/daftar-anggota";
    }
}
