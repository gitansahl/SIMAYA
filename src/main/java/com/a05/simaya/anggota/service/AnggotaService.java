package com.a05.simaya.anggota.service;

import com.a05.simaya.anggota.model.AnggotaModel;
import com.a05.simaya.anggota.payload.AnggotaDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AnggotaService {
    void tambahAnggota(AnggotaDTO anggota);
    List<AnggotaModel> getListAnggota();
    AnggotaDTO getInfoAnggota(String id);
    void updateDataAnggota(AnggotaDTO updateAnggota);
    String encrypt(String password);
    AnggotaModel getAnggotaByUsername(String username);
    AnggotaModel getAnggotaById(String id);
    boolean cekPassword(String id, String oldPassword);
    void gantiPassword(String id, String newPassword);

    String uploadProfile(MultipartFile image, String username, String pastUrl) throws IOException;
}
