package com.a05.simaya.anggota.controller;

import com.a05.simaya.anggota.model.AnggotaModel;
import com.a05.simaya.anggota.service.AnggotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AnggotaRestController {
    @Autowired
    private AnggotaService anggotaService;

    @RequestMapping(value = "/anggota/viewall", method = RequestMethod.GET)
    private ResponseEntity getAllAnggota() {
        ResponseEntity responseEntity = null;
        try {
            List<AnggotaModel> listAnggota = anggotaService.getListAnggota();
            responseEntity = ResponseEntity.ok(listAnggota);
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;

    }

}
