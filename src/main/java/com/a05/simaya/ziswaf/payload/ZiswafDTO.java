package com.a05.simaya.ziswaf.payload;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ZiswafDTO {
    private String id;
    private String donatur;
    private Long jumlah = 0L;
    private String noTelp;
    private String jenisZiswaf;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate donatedDate;
}
