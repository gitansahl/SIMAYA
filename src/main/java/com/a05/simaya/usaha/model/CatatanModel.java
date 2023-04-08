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
@Table(name = "catatan")
public class CatatanModel {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String idCatatan;

    @NotNull
    @Column(name = "catatan", columnDefinition = "TEXT")
    private String catatan;

    @OneToOne
    @JoinColumn(name="id_usaha", nullable = false)
    private UsahaModel usaha;
}
