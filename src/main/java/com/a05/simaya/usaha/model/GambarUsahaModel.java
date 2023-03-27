package com.a05.simaya.usaha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "gambar_usaha")
public class GambarUsahaModel {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String idGambar;

    @ManyToOne
    @JoinColumn(name="id_usaha", nullable=false)
    private UsahaModel usahaModel;

    @NotNull
    @Column(name = "gambar_usaha")
    private String gambarUsaha;

    @Transient
    public String getPhotosImagePath() {
        return "/usaha-photos/" + gambarUsaha;
    }
}
