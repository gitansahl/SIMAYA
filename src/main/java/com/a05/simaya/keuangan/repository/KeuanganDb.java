package com.a05.simaya.keuangan.repository;

import com.a05.simaya.keuangan.model.KeuanganModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeuanganDb extends JpaRepository<KeuanganModel, Long> {

}
