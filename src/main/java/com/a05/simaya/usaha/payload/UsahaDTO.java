package com.a05.simaya.usaha.payload;

import lombok.Data;

import java.util.List;

@Data
public class UsahaDTO {
    private String idUsaha;
    private String username;
    private String namaProduk;
    private int hargaProduk;
    private String namaPenjual;
    private String kontakPenjual;
    private String deskripsiProduk;
    private List<String> gambarUsaha;
}
