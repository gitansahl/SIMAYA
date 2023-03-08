package com.a05.simaya.anggota.service;

import com.a05.simaya.anggota.model.AnggotaModel;
import com.a05.simaya.anggota.model.GolDarEnum;
import com.a05.simaya.anggota.model.RoleEnum;
import com.a05.simaya.anggota.payload.CreateAnggotaDTO;
import com.a05.simaya.anggota.repository.AnggotaDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnggotaServiceImpl implements AnggotaService {

    @Autowired
    AnggotaDb anggotaDb;
    @Override
    public void tambahAnggota(CreateAnggotaDTO anggota) {
        anggotaDb.save(makeAnggotaModel(anggota));
    }

    private AnggotaModel makeAnggotaModel(CreateAnggotaDTO anggota) {
        AnggotaModel anggotaModel = new AnggotaModel();
        anggotaModel.setRole(RoleEnum.valueOf(anggota.getRole()));
        anggotaModel.setUsername(anggota.getUsername());
        anggotaModel.setPassword(anggota.getPassword());
        anggotaModel.setEmail(anggota.getEmail());
        anggotaModel.setTanggalLahir(anggota.getTanggalLahir());
        anggotaModel.setTempatLahir(anggota.getTempatLahir());
        anggotaModel.setGolonganDarah(GolDarEnum.valueOf(anggota.getGolonganDarah()));
        anggotaModel.setJenisKelamin(anggota.getJenisKelamin());
        anggotaModel.setNamaDepan(anggota.getTempatLahir());
        anggotaModel.setNamaBelakang(anggota.getNamaBelakang());
        anggotaModel.setNomorHP(anggota.getNomorHP());

        return anggotaModel;
    }
}
