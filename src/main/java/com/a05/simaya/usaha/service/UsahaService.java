package com.a05.simaya.usaha.service;

import com.a05.simaya.usaha.model.GambarUsahaModel;
import com.a05.simaya.usaha.model.UsahaModel;
import com.a05.simaya.usaha.payload.UsahaDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UsahaService {
    UsahaModel tambahUsaha(UsahaDTO usahaDTO);
    UsahaModel ubahUsaha(UsahaDTO usahaDTO);
    UsahaModel getUsaha(String id);
    Boolean hapusUsaha(String id);
    void verifikasiUsaha(String id);
    List<GambarUsahaModel> uploadPhoto(MultipartFile[] image, UsahaModel usahaModel) throws IOException;
    UsahaDTO getDataUsaha(String id);
    UsahaDTO getDataPenjual(String name);
    void simpanPhoto(UsahaModel usahaModel, List<GambarUsahaModel> files);

    List<UsahaModel> getListUsaha();

    void tolakUsaha(String id, String catatan);
}
