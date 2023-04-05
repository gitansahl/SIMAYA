package com.a05.simaya.keuangan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "keuangan")
public class KeuanganModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tipe")
    // 1 = Pemasukan, 0 = Pengeluaran
    private Boolean tipe;

    @NotNull
    @Column(name = "jumlah")
    private Long jumlah;

    @NotNull
    @Column(name = "jenis")
    @Enumerated(EnumType.STRING)
    private JenisEnum jenis;

    @Column(name = "deskripsi")
    private String deskripsi;

    @NotNull
    @Column(name = "waktu_dicatat")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktuDicatat;
}
