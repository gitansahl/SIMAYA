package com.a05.simaya.usaha.repository;

import com.a05.simaya.usaha.model.UsahaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsahaDb extends JpaRepository<UsahaModel, String> {
    UsahaModel getByIdUsaha(String id);
    Optional<UsahaModel> findByIdUsaha(String id);
}
