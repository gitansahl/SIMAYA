package com.a05.simaya.ziswaf.controller;

import com.a05.simaya.event.model.EventModel;
import com.a05.simaya.ziswaf.model.JenisZiswafEnum;
import com.a05.simaya.ziswaf.model.ZiswafModel;
import com.a05.simaya.ziswaf.payload.ZiswafDTO;
import com.a05.simaya.ziswaf.service.ZiswafService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class ZiswafController {
    @Autowired
    private ZiswafService ziswafService;

    @GetMapping(value = "/tambah-ziswaf")
    public String formTambahZisnaf(Model model) {
        model.addAttribute("ziswaf", new ZiswafDTO());
        return "ziswaf/tambah-donasi-ziswaf";
    }

    @PostMapping(value = "/tambah-ziswaf")
    public String tambahZisnaf(ZiswafDTO ziswafDTO,
                              RedirectAttributes redirectAttributes) {
        ZiswafModel ziswafModel = ziswafService.tambahDonasiZizwaf(ziswafDTO);

        String jenisZiswafEnum = String.valueOf(ziswafModel.getJenisZiswaf());
        String[] list = jenisZiswafEnum.split("_");
        StringBuilder result = new StringBuilder();

        for (String str : list) {
            result.append(str.charAt(0));
            result.append(str.substring(1).toLowerCase());
            result.append(" ");
        }

        redirectAttributes.addFlashAttribute("success", String.format("Donasi Ziswaf dengan jenis %s sebesar Rp %d berhasil ditambahkan",
                result, ziswafModel.getJumlah()));

        return "redirect:/daftar-ziswaf";
    }

    @GetMapping(value = "/daftar-ziswaf")
    public String getDaftarZiswaf() {
        return "ziswaf/daftar-donasi-ziswaf";
    }

    @GetMapping(value = "/ziswaf")
    public String getDashboardEvent(Model model) {
        Long countZiswafToday = ziswafService.getZiswafToday();
        Long countZiswafLastWeek = ziswafService.getZiswafLastWeek();
        Long countZiswafLastMonth = ziswafService.getZiswafLastMonth();
        model.addAttribute("today", countZiswafToday);
        model.addAttribute("week", countZiswafLastWeek);
        model.addAttribute("month", countZiswafLastMonth);
        return "ziswaf/dashboard-ziswaf";
    }
}
