package com.a05.simaya.anggota.service;

import com.a05.simaya.anggota.model.*;
import com.a05.simaya.anggota.payload.AnggotaDTO;
import com.a05.simaya.anggota.repository.AnggotaDb;
import com.a05.simaya.anggota.repository.ProfileAnggotaDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AnggotaServiceImpl implements AnggotaService {

    @Autowired
    AnggotaDb anggotaDb;

    @Autowired
    ProfileAnggotaDb profileAnggotaDb;

    @Override
    public void tambahAnggota(AnggotaDTO anggota) {
        AnggotaModel anggotaModel = setAnggotaModel(anggota, new AnggotaModel());
        ProfileModel profileModel = setProfileAnggota(anggota.getProfile(), anggotaModel.getProfile());
        anggotaDb.save(anggotaModel);
        profileAnggotaDb.save(profileModel);
    }

    @Override
    public List<AnggotaModel> getListAnggota() {
        return anggotaDb.findAll();
    }

    @Override
    public AnggotaDTO getInfoAnggota(String id) {
        AnggotaDTO updateAnggotaDTO = new AnggotaDTO();
        AnggotaModel anggotaModel = anggotaDb.findAnggotaModelById(id);

        updateAnggotaDTO.setId(id);
        updateAnggotaDTO.setUsername(anggotaModel.getUsername());
        updateAnggotaDTO.setPassword(anggotaModel.getPassword());
        updateAnggotaDTO.setNamaDepan(anggotaModel.getNamaDepan());
        updateAnggotaDTO.setNamaBelakang(anggotaModel.getNamaBelakang());
        updateAnggotaDTO.setEmail(anggotaModel.getEmail());
        updateAnggotaDTO.setNomorHP(anggotaModel.getNomorHP());
        updateAnggotaDTO.setRole(String.valueOf(anggotaModel.getRole()));
        updateAnggotaDTO.setGolonganDarah(String.valueOf(anggotaModel.getGolonganDarah()));
        updateAnggotaDTO.setTempatLahir(anggotaModel.getTempatLahir());
        updateAnggotaDTO.setTanggalLahir(anggotaModel.getTanggalLahir());
        updateAnggotaDTO.setJenisKelamin(anggotaModel.getJenisKelamin());
        updateAnggotaDTO.setStatusKeanggotaan(anggotaModel.getStatusKeanggotaan());

        updateAnggotaDTO.setProfile(anggotaModel.getProfile());

        return updateAnggotaDTO;
    }

    @Override
    public void updateDataAnggota(AnggotaDTO anggotaDTO) {
        AnggotaModel anggota = anggotaDb.findAnggotaModelById(anggotaDTO.getId());
        AnggotaModel updatedAnggota = setAnggotaModel(anggotaDTO, anggota);
        ProfileModel profileModel = profileAnggotaDb.findProfileModelByAnggota_Id(anggotaDTO.getId());
        ProfileModel updateProfile = setProfileAnggota(anggotaDTO.getProfile(), profileModel);
        anggotaDb.save(updatedAnggota);
        profileAnggotaDb.save(updateProfile);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    private AnggotaModel setAnggotaModel(AnggotaDTO anggotaDTO, AnggotaModel anggotaModel) {
        anggotaModel.setRole(RoleEnum.valueOf(anggotaDTO.getRole()));
        anggotaModel.setUsername(anggotaDTO.getUsername());
        anggotaModel.setPassword(encrypt(anggotaDTO.getPassword()));
        anggotaModel.setEmail(anggotaDTO.getEmail());
        anggotaModel.setTanggalLahir(anggotaDTO.getTanggalLahir());
        anggotaModel.setTempatLahir(anggotaDTO.getTempatLahir());
        anggotaModel.setGolonganDarah(GolDarEnum.valueOf(anggotaDTO.getGolonganDarah()));
        anggotaModel.setJenisKelamin(anggotaDTO.getJenisKelamin());
        anggotaModel.setNamaDepan(anggotaDTO.getNamaDepan());
        anggotaModel.setNamaBelakang(anggotaDTO.getNamaBelakang());
        anggotaModel.setNomorHP(anggotaDTO.getNomorHP());
        anggotaModel.setStatusKeanggotaan(anggotaDTO.getStatusKeanggotaan());
        anggotaModel.setProfile(anggotaDTO.getProfile());

        return anggotaModel;
    }

    private ProfileModel setProfileAnggota(ProfileModel profileDTO, ProfileModel profileModel) {
        profileModel.setDivisi(profileDTO.getDivisi());
        profileModel.setPekerjaan(profileDTO.getPekerjaan());
        profileModel.setIsMengelolaMentoring(profileDTO.getIsMengelolaMentoring());
        profileModel.setIsPunyaMobil(profileDTO.getIsPunyaMobil());
        profileModel.setIsPunyaMotor(profileDTO.getIsPunyaMotor());
        profileModel.setIsPunyaRumah(profileDTO.getIsPunyaRumah());
        profileModel.setIsPunyaVila(profileDTO.getIsPunyaVila());
        profileModel.setCatatan(profileDTO.getCatatan());

        return profileModel;
    }

}
