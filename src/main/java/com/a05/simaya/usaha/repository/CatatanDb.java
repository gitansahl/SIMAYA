package com.a05.simaya.usaha.repository;

import com.a05.simaya.usaha.model.CatatanModel;
import com.a05.simaya.usaha.model.UsahaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatatanDb  extends JpaRepository<CatatanModel, String> {
    CatatanModel findCatatanModelByUsaha(UsahaModel usaha);
}
