package com.a05.simaya.anggota.model;

import com.a05.simaya.event.model.EventModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "anggota")
public class AnggotaModel {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @NotNull
    @Column(name = "username", unique = true)
    private String username;

    @NotNull
    @Lob
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "tanggal_lahir")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalLahir;

    @NotNull
    @Column(name = "tempat_lahir")
    private String tempatLahir;

    @Column(name = "golongan_darah")
    @Enumerated(EnumType.STRING)
    private GolDarEnum golonganDarah;

    @NotNull
    @Column(name = "jenis_kelamin")
    private Boolean jenisKelamin;

    @NotNull
    @Column(name = "nama_depan")
    private String namaDepan;

    @NotNull
    @Column(name = "nama_belakang")
    private String namaBelakang;

    @NotNull
    @Column(name = "nomor_hp")
    private String nomorHP;

    @NotNull
    @Column(name = "status_keanggotaan")
    private Boolean statusKeanggotaan = Boolean.TRUE;

    @OneToMany(mappedBy = "penanggungJawab", fetch = FetchType.LAZY)
    private List<EventModel> listEvent;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_profile", referencedColumnName = "id_profile")
    private ProfileModel profile;
}
