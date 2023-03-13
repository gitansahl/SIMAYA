package com.a05.simaya.anggota.repository;

import com.a05.simaya.anggota.model.ProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileAnggotaDb extends JpaRepository<ProfileModel, String> {
    ProfileModel findProfileModelByAnggota_Id(String id);
}
