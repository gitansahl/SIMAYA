package com.a05.simaya.anggota.payload;

import com.a05.simaya.anggota.model.ProfileModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class AnggotaDTO {
    private String id;
    private String role;
    private String username;
    private String password;
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalLahir;

    private String tempatLahir;
    private String golonganDarah;
    private Boolean jenisKelamin;
    private String namaDepan;
    private String namaBelakang;
    private String nomorHP;
    private Boolean statusKeanggotaan = Boolean.TRUE;
    private ProfileModel profile;
}
