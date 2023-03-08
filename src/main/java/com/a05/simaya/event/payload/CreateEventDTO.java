package com.a05.simaya.event.payload;

import com.a05.simaya.anggota.model.AnggotaModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CreateEventDTO {

    private String namaEvent;
    private String direktorat;
    private String programKerja;
    private String tempatPelaksanaan;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktuMulai;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktuAkhir;

    private String deskripsi;
    private Boolean isDeleted;
    private AnggotaModel penanggungJawab;



}
