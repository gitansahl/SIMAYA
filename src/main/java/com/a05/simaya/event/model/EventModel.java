package com.a05.simaya.event.model;

import com.a05.simaya.anggota.model.AnggotaModel;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
@Table(name = "event")
public class EventModel {

    @Id
    @Column(name = "id_event")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvent;

    @NotNull
    @Column(name = "nama_event")
    private String namaEvent;

    @NotNull
    @Column(name = "direktorat")
    private DirektoratEnum direktorat;

    @NotNull
    @Column(name = "program_kerja")
    private String programKerja;

    @NotNull
    @Column(name = "tempat_pelaksanaan")
    private String tempatPelaksanaan;

    @NotNull
    @Column(name = "waktu_mulai")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktuMulai;

    @NotNull
    @Column(name = "waktu_akhir")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktuAkhir;

    @Column(name = "deskripsi")
    private String deskripsi;

    @NotNull
    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "uuid_pj", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private AnggotaModel penanggungJawab;

    @JsonManagedReference
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProgresModel> listProgres;
}
