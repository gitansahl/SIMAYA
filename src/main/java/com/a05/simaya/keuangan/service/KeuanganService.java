package com.a05.simaya.keuangan.service;

import com.a05.simaya.keuangan.model.KeuanganModel;
import java.util.List;
import com.a05.simaya.keuangan.payload.KeuanganDTO;

public interface KeuanganService {
    void tambahPemasukan(KeuanganDTO keuanganDTO);
    void tambahPengeluaran(KeuanganDTO keuanganDTO);
    List<KeuanganModel> getListKeuangan();
    List<KeuanganModel> getListPengeluaranZiswaf();
}
