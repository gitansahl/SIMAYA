package com.a05.simaya.anggota.service;

import com.a05.simaya.anggota.model.AnggotaModel;
import com.a05.simaya.anggota.payload.CreateAnggotaDTO;

import java.util.List;

public interface AnggotaService {
    void tambahAnggota(CreateAnggotaDTO anggota);
    List<AnggotaModel> getListAnggota();
}
