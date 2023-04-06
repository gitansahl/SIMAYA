package com.a05.simaya.keuangan.service;

import com.a05.simaya.keuangan.model.JenisEnum;
import com.a05.simaya.keuangan.model.KeuanganModel;
import com.a05.simaya.keuangan.payload.KeuanganDTO;
import com.a05.simaya.keuangan.repository.KeuanganDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class KeuanganServiceImpl implements KeuanganService{

    @Autowired
    KeuanganDb keuanganDb;

    @Override
    public void tambahPemasukan(KeuanganDTO keuanganDTO) {
        KeuanganModel keuanganModel = setKeuanganModel(keuanganDTO, new KeuanganModel(), true);
        keuanganDb.save(keuanganModel);
    }

    @Override
    public void tambahPengeluaran(KeuanganDTO keuanganDTO) {
        KeuanganModel keuanganModel = setKeuanganModel(keuanganDTO, new KeuanganModel(), false);
        keuanganDb.save(keuanganModel);
    }

    private KeuanganModel setKeuanganModel(KeuanganDTO keuanganDTO, KeuanganModel keuanganModel, Boolean tipe){
        keuanganModel.setJumlah(keuanganDTO.getJumlah());
        keuanganModel.setTipe(tipe);
        keuanganModel.setDeskripsi(keuanganDTO.getDeskripsi());
        keuanganModel.setJenis(JenisEnum.valueOf(keuanganDTO.getJenis()));
        keuanganModel.setTanggalTransaksi(keuanganDTO.getTanggalTransaksi());
        keuanganModel.setWaktuDicatat(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return keuanganModel;
    }
}
