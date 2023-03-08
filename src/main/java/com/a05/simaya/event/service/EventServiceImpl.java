package com.a05.simaya.event.service;

import com.a05.simaya.anggota.model.AnggotaModel;
import com.a05.simaya.anggota.model.GolDarEnum;
import com.a05.simaya.anggota.model.RoleEnum;
import com.a05.simaya.anggota.payload.CreateAnggotaDTO;
import com.a05.simaya.event.model.DirektoratEnum;
import com.a05.simaya.event.model.EventModel;
import com.a05.simaya.event.payload.CreateEventDTO;
import com.a05.simaya.event.repository.EventDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    EventDb eventDb;

    @Override
    public void tambahEvent(CreateEventDTO eventDTO) {
        eventDb.save(makeEventModel(eventDTO));
    }

    private EventModel makeEventModel(CreateEventDTO eventDTO) {
        EventModel eventModel = new EventModel();
        eventModel.setNamaEvent(eventDTO.getNamaEvent());
        eventModel.setDeskripsi(eventDTO.getDeskripsi());
        eventModel.setDirektorat(DirektoratEnum.valueOf(eventDTO.getDirektorat()));
        eventModel.setProgramKerja(eventDTO.getProgramKerja());
        eventModel.setTempatPelaksanaan(eventDTO.getTempatPelaksanaan());
        eventModel.setWaktuMulai(eventDTO.getWaktuMulai());
        eventModel.setWaktuAkhir(eventDTO.getWaktuAkhir());
        eventModel.setPenanggungJawab(eventDTO.getPenanggungJawab());

        return eventModel;
    }
}
