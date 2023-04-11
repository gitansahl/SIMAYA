package com.a05.simaya.usaha.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "last_edit")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime lastEdit;

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

    @JsonIgnore
    @OneToMany(mappedBy="usahaModel")
    private List<GambarUsahaModel> gambar;

    @Column(name = "status_usaha")
    @Enumerated(EnumType.STRING)
    private StatusUsaha statusUsaha = StatusUsaha.BELUM_TERVERIFIKASI;


    @JsonIgnore
    @OneToOne(mappedBy = "usaha", optional = true)
    private CatatanModel catatan;

}
