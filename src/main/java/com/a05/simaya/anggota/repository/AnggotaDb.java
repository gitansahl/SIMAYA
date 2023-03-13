package com.a05.simaya.anggota.repository;

import com.a05.simaya.anggota.model.AnggotaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnggotaDb extends JpaRepository<AnggotaModel, String> {
    AnggotaModel findAnggotaModelById(String id);
}
