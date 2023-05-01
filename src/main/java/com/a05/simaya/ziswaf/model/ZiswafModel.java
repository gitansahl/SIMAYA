package com.a05.simaya.ziswaf.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ziswaf")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ZiswafModel {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    @NotNull
    @Column(name = "donatur")
    private String donatur;

    @NotNull
    @Column(name = "jumlah")
    private Long jumlah;

    @NotNull
    @Column(name= "no_telp")
    private String noTelp;

    @Enumerated(EnumType.STRING)
    @Column(name = "jenis_ziswaf")
    private JenisZiswafEnum jenisZiswaf;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "donated_date")
    private LocalDate donatedDate;
}
