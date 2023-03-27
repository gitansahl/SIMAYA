package com.a05.simaya.usaha.repository;

import com.a05.simaya.usaha.model.GambarUsahaModel;
import com.a05.simaya.usaha.model.UsahaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GambarUsahaDb extends JpaRepository<GambarUsahaModel, String> {
    List<GambarUsahaModel> findAllByUsahaModel(UsahaModel usahaModel);
}
