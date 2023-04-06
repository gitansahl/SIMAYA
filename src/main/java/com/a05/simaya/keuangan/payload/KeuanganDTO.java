package com.a05.simaya.keuangan.payload;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class KeuanganDTO {
    private Long id;
    private Boolean tipe;
    private Long jumlah;
    private String jenis;
    private String deskripsi;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalTransaksi;

}

