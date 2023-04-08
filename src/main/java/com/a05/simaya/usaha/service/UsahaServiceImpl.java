package com.a05.simaya.usaha.service;

import com.a05.simaya.anggota.model.AnggotaModel;
import com.a05.simaya.anggota.repository.AnggotaDb;
import com.a05.simaya.usaha.model.CatatanModel;
import com.a05.simaya.usaha.model.GambarUsahaModel;
import com.a05.simaya.usaha.model.StatusUsaha;
import com.a05.simaya.usaha.model.UsahaModel;
import com.a05.simaya.usaha.payload.UsahaDTO;
import com.a05.simaya.usaha.repository.CatatanDb;
import com.a05.simaya.usaha.repository.GambarUsahaDb;
import com.a05.simaya.usaha.repository.UsahaDb;
import com.a05.simaya.usaha.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsahaServiceImpl implements UsahaService {

    @Autowired
    private UsahaDb usahaDb;

    @Autowired
    private GambarUsahaDb gambarUsahaDb;

    @Autowired
    private AnggotaDb anggotaDb;

    @Autowired
    private CatatanDb catatanDb;

    @Override
    public UsahaModel tambahUsaha(UsahaDTO usahaDTO) {
        UsahaModel usahaModel = setUsahaModel(usahaDTO, new UsahaModel());
        usahaModel.setLastEdit(LocalDateTime.now());
        usahaDb.save(usahaModel);

        return usahaModel;
    }

    @Override
    public UsahaModel ubahUsaha(UsahaDTO usahaDTO) {
        UsahaModel pastUsahaModel = usahaDb.getByIdUsaha(usahaDTO.getIdUsaha());
        UsahaModel usahaModel = setUsahaModel(usahaDTO, pastUsahaModel);
        usahaModel.setIdUsaha(usahaDTO.getIdUsaha());
        if (usahaModel.getStatusUsaha() != StatusUsaha.BELUM_TERVERIFIKASI) {
            usahaModel.setLastEdit(LocalDateTime.now());
            usahaModel.setStatusUsaha(StatusUsaha.BELUM_TERVERIFIKASI);
        }
        usahaDb.save(usahaModel);

        return usahaModel;
    }

    @Override
    public UsahaModel getUsaha(String id) {
        return usahaDb.getByIdUsaha(id);
    }

    @Override
    public Boolean hapusUsaha(String id) {
        UsahaModel usahaModel = usahaDb.getByIdUsaha(id);

        Optional<UsahaModel> UsahaModel = usahaDb.findByIdUsaha(id);
        UsahaModel usaha = UsahaModel.orElse(null);
        if (usaha != null){
            List<GambarUsahaModel> gambarUsahaModels = gambarUsahaDb.findAllByUsahaModel(usaha);
            gambarUsahaDb.deleteAll(gambarUsahaModels);
            usahaDb.delete(usahaModel);
            return true;
        }
        return false;
    }

    @Override
    public void verifikasiUsaha(String id) {
        UsahaModel usahaModel = usahaDb.getByIdUsaha(id);
        usahaModel.setStatusUsaha(StatusUsaha.TERVERIFIKASI);
        usahaDb.save(usahaModel);
    }

    @Override
    public void tolakUsaha(String id, String catatan) {
        UsahaModel usahaModel = usahaDb.getByIdUsaha(id);
        usahaModel.setStatusUsaha(StatusUsaha.TIDAK_TERVERIFIKASI);

        CatatanModel catatanModel = new CatatanModel();
        catatanModel.setCatatan(catatan);
        catatanModel.setUsaha(usahaModel);
        catatanDb.save(catatanModel);
    }

    @Override
    public List<UsahaModel> getUsahaByUsername(String username) {
        return usahaDb.findAllByUsernameIs(username);
    }

    @Override
    public List<GambarUsahaModel> uploadPhoto(MultipartFile[] images, UsahaModel usahaModel) throws IOException {
        deletePhotosFiles(usahaModel);
        return savePhotosFiles(images, usahaModel);
    }

    private void deletePhotosFiles(UsahaModel usahaModel) throws IOException {
        List<GambarUsahaModel> pastUploadedFiles = gambarUsahaDb.findAllByUsahaModel(usahaModel);
        for (GambarUsahaModel pastGambar : pastUploadedFiles) {
            System.out.println("src/main/resources/static" + pastGambar.getPhotosImagePath());
            FileUploadUtil.deleteFile("src/main/resources/static" + pastGambar.getPhotosImagePath());
            gambarUsahaDb.delete(pastGambar);
        }

    }

    private List<GambarUsahaModel> savePhotosFiles(MultipartFile[] images, UsahaModel usahaModel) throws IOException {
        List<GambarUsahaModel> uploadedFileNames = new ArrayList<>();
        for (int i = 0; i<images.length; i++) {
            MultipartFile image = images[i];
            if (image.isEmpty())
                return null;

            String fileName = StringUtils.cleanPath(image.getOriginalFilename());

            String[] stringSplitted = fileName.split("\\.");
            String extension = stringSplitted[stringSplitted.length-1];

            String uploadedFileName = usahaModel.getIdUsaha() + "-" + i + "." + extension;
            GambarUsahaModel gambarUsahaModel = new GambarUsahaModel();
            gambarUsahaModel.setGambarUsaha(uploadedFileName);
            gambarUsahaModel.setUsahaModel(usahaModel);
            gambarUsahaDb.save(gambarUsahaModel);
            uploadedFileNames.add(gambarUsahaModel);

            FileUploadUtil.saveFile("src/main/resources/static/usaha-photos/", usahaModel.getIdUsaha() + "-" + i + "." + extension, image);
        }

        return uploadedFileNames;
    }

    @Override
    public UsahaDTO getDataUsaha(String id) {
        UsahaModel usahaModel = usahaDb.getByIdUsaha(id);
        UsahaDTO usahaDTO = new UsahaDTO();

        usahaDTO.setIdUsaha(id);
        usahaDTO.setNamaProduk(usahaModel.getNamaProduk());
        usahaDTO.setHargaProduk(usahaModel.getHargaProduk());
        usahaDTO.setNamaPenjual(usahaModel.getNamaPenjual());
        usahaDTO.setKontakPenjual(usahaModel.getKontakPenjual());
        usahaDTO.setDeskripsiProduk(usahaModel.getDeskripsiProduk());

        List<String> filenames = new ArrayList<>();
        for (int i=0; i<usahaModel.getGambar().size(); i++) {
            filenames.add(usahaModel.getGambar().get(i).getPhotosImagePath());
        }
        usahaDTO.setGambarUsaha(filenames);

        return usahaDTO;
    }

    @Override
    public UsahaDTO getDataPenjual(String name) {
        UsahaDTO usahaDTO = new UsahaDTO();
        AnggotaModel anggotaModel = anggotaDb.findByUsername(name);
        usahaDTO.setNamaPenjual(anggotaModel.getNamaDepan() + " " + anggotaModel.getNamaBelakang());
        usahaDTO.setKontakPenjual(anggotaModel.getNomorHP());
        return usahaDTO;
    }

    @Override
    public void simpanPhoto(UsahaModel usahaModel, List<GambarUsahaModel> files) {
        usahaModel.setGambar(files);
        usahaDb.save(usahaModel);
    }

    @Override
    public List<UsahaModel> getUsahaByStatus(StatusUsaha status) {
        return usahaDb.findAllByStatusUsahaIs(status);
    }

    private UsahaModel setUsahaModel(UsahaDTO usahaDTO, UsahaModel usahaModel) {
        usahaModel.setUsername(usahaDTO.getUsername());
        usahaModel.setNamaProduk(usahaDTO.getNamaProduk());
        usahaModel.setHargaProduk(usahaDTO.getHargaProduk());
        usahaModel.setNamaPenjual(usahaDTO.getNamaPenjual());
        usahaModel.setKontakPenjual(usahaDTO.getKontakPenjual());
        usahaModel.setDeskripsiProduk(usahaDTO.getDeskripsiProduk());

        return usahaModel;
    }

    @Override
    public List<UsahaModel> getListUsaha(){
        return usahaDb.findAll();
    }
}
