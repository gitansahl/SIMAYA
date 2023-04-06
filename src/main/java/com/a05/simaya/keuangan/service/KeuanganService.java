package com.a05.simaya.keuangan.service;

import com.a05.simaya.keuangan.payload.KeuanganDTO;

public interface KeuanganService {
    void tambahPemasukan(KeuanganDTO keuanganDTO);
    void tambahPengeluaran(KeuanganDTO keuanganDTO);
}
