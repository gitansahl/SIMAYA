package com.a05.simaya.usaha.controller;

import com.a05.simaya.usaha.service.UsahaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class UsahaRestController {
    @Autowired
    private UsahaService usahaService;

    @PutMapping(value = "/usaha/verifikasi")
    public String verifikasiUsaha(@RequestBody Map<String, String> mapRequest) {
        usahaService.verifikasiUsaha(mapRequest.get("id"));
        return "redirect:/detail-usaha/" + mapRequest.get("id");
    }
}
