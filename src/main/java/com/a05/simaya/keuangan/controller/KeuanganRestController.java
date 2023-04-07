package com.a05.simaya.keuangan.controller;

import com.a05.simaya.event.model.EventModel;
import com.a05.simaya.keuangan.model.KeuanganModel;
import com.a05.simaya.keuangan.service.KeuanganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class KeuanganRestController {
    @Autowired
    private KeuanganService keuanganService;

    @GetMapping(value = "/ringkasan-keuangan")
    private ResponseEntity ringkasanKeuangan(){
        ResponseEntity responseEntity = null;
        try{
            List<KeuanganModel> listKeuangan = keuanganService.getListKeuangan();
            responseEntity = ResponseEntity.ok(listKeuangan);
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
