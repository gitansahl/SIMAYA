package com.a05.simaya.ziswaf.controller;

import com.a05.simaya.keuangan.model.KeuanganModel;
import com.a05.simaya.keuangan.service.KeuanganService;
import com.a05.simaya.ziswaf.model.ZiswafModel;
import com.a05.simaya.ziswaf.service.ZiswafService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ZiswafRestController {
    @Autowired
    private ZiswafService ziswafService;
    @Autowired
    private KeuanganService keuanganService;

    @GetMapping(value = "/pemasukan-ziswaf")
    private ResponseEntity ringkasanPemasukanZiswaf(){
        ResponseEntity responseEntity = null;
        try{
            List<ZiswafModel> listPemasukanZiswaf = ziswafService.getListPemasukan();
            responseEntity = ResponseEntity.ok(listPemasukanZiswaf);
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping(value = "/pengeluaran-ziswaf")
    private ResponseEntity ringkasanPengeluaranZiswaf() {
        ResponseEntity responseEntity = null;
        try {
            List<KeuanganModel> listKeuangan = keuanganService.getListPengeluaranZiswaf();
            responseEntity = ResponseEntity.ok(listKeuangan);
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping(value = "/bar_chart")
    public ResponseEntity getBarChartData() {
        ResponseEntity response;

        try {
            Object responseMap = ziswafService.getBarChartData();
            response = ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @GetMapping(value = "/line_chart")
    public ResponseEntity getLineChartDataDaily() {
        ResponseEntity response;

        try {
            Object responseMap = ziswafService.getLineChartDataDaily();
            response = ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @GetMapping(value = "/line_chart_monthly")
    public ResponseEntity getLineChartMonthly() {
        ResponseEntity response;

        try {
            Object responseMap = ziswafService.getLineChartDataMonthly();
            response = ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
