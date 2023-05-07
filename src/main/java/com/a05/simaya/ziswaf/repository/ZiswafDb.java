package com.a05.simaya.ziswaf.repository;

import com.a05.simaya.ziswaf.model.ZiswafModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZiswafDb extends JpaRepository<ZiswafModel, String> {
}
