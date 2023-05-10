package com.a05.simaya.anggota.model;

import com.a05.simaya.event.model.DirektoratEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "profile")
public class ProfileModel {
    @Id
    @Column(name = "id_profile")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfile;

    @Column(name = "divisi")
    @Enumerated(EnumType.STRING)
    private DirektoratEnum divisi;

    @Column(name = "pekerjaan")
    @Enumerated(EnumType.STRING)
    private JobEnum pekerjaan;

    @Column(name = "mengelola_mentoring")
    private Boolean isMengelolaMentoring = Boolean.FALSE;

    @Column(name = "punya_mobil")
    private Boolean isPunyaMobil = Boolean.FALSE;

    @Column(name = "punya_motor")
    private Boolean isPunyaMotor = Boolean.FALSE;

    @Column(name = "punya_rumah")
    private Boolean isPunyaRumah = Boolean.FALSE;

    @Column(name = "punya_vila")
    private Boolean isPunyaVila = Boolean.FALSE;

    @Column(name = "catatan")
    private String catatan;

    @Column(name = "photo")
    private String photoUrl;

    @Transient
    public String getPhotosImagePath() {
        if (photoUrl == null) return null;

        return photoUrl;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "profile")
    @JsonIgnore
    private AnggotaModel anggota;
}
