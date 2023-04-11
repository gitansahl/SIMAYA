package com.a05.simaya.usaha.controller;

import com.a05.simaya.usaha.model.StatusUsaha;
import com.a05.simaya.usaha.model.UsahaModel;
import com.a05.simaya.usaha.service.UsahaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping(value = "/usaha/daftar-usaha-verifikasi")
    public ResponseEntity getUsahaPerluVerifikasi() {
        ResponseEntity responseEntity = null;
        try {
            List<UsahaModel> listUsaha = usahaService.getUsahaByStatus(StatusUsaha.BELUM_TERVERIFIKASI);
            responseEntity = ResponseEntity.ok(listUsaha);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping(value="usaha/tolak")
    public String tolakUsaha(@RequestBody Map<String, String> mapRequest){
        usahaService.tolakUsaha(mapRequest.get("idUsaha"), mapRequest.get("catatan"));
        return "redirect:/detail-usaha/" + mapRequest.get("id");
    }
}
