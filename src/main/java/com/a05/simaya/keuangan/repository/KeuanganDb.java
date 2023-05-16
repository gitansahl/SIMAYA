package com.a05.simaya.keuangan.repository;

import com.a05.simaya.keuangan.model.KeuanganModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeuanganDb extends JpaRepository<KeuanganModel, Long> {
    @Query("SELECT k FROM KeuanganModel k WHERE k.jenis = 'ZISWAF' AND k.tipe = false")
    List<KeuanganModel> findPengeluaranZiswaf();
}
