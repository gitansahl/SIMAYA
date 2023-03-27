package com.a05.simaya.usaha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "usaha")
public class UsahaModel {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String idUsaha;

    @NotNull
    @Column(name = "nama_produk")
    private String namaProduk;

    @NotNull
    @Column(name = "harga_produk")
    private int hargaProduk = 0;

    @NotNull
    @Column(name = "nama_penjual")
    private String namaPenjual;

    @NotNull
    @Column(name = "kontak_penjual")
    private String kontakPenjual;

    @NotNull
    @Column(name = "deskripsi_produk")
    private String deskripsiProduk;

    @OneToMany(mappedBy="usahaModel")
    private List<GambarUsahaModel> gambar;

    @Column(name = "status_usaha")
    @Enumerated(EnumType.STRING)
    private StatusUsaha statusUsaha = StatusUsaha.BELUM_TERVERIFIKASI;

}
