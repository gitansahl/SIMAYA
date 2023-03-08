package com.a05.simaya.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "progres")
public class ProgresModel {
    @Id
    @Column(name = "id_progres")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProgres;

    @NotNull
    @Column(name = "nama")
    private String nama;

    @NotNull
    @Column(name = "status")
    private Boolean status = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_event", referencedColumnName = "id_event")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private EventModel event;
}
