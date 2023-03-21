package com.a05.simaya.event.service;

import com.a05.simaya.anggota.model.AnggotaModel;
import com.a05.simaya.event.model.DirektoratEnum;
import com.a05.simaya.event.model.EventModel;
import com.a05.simaya.event.payload.CreateEventDTO;
import com.a05.simaya.event.repository.EventDb;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    EventDb eventDb;

    @Override
    public Boolean tambahEvent(CreateEventDTO eventDTO) {
        if (eventDb.getEventModelByNamaEvent(eventDTO.getNamaEvent()) != null){
            return  false;
        }
        eventDb.save(makeEventModel(eventDTO));
        return true;
    }

    @Override
    public List<EventModel> getListOngoing() {
        List<EventModel> listEvent = eventDb.findAll();
        if (listEvent.size() == 0){
            return null;
        } else {
            LocalDateTime now = LocalDateTime.now();
            List<EventModel> listOngoing = new ArrayList<>();
            for (EventModel event: listEvent){
                if (event.getWaktuMulai().isBefore(now) && event.getWaktuAkhir().isAfter(now) && !event.getIsDeleted()){
                    listOngoing.add(event);
                }
            }
            if (listOngoing.size() == 0){
                return null;
            }
            return listOngoing;
        }
    }

    @Override
    public List<EventModel> getListUpcoming() {
        List<EventModel> listEvent = eventDb.findAll();
        if (listEvent.size() == 0){
            return null;
        } else {
            LocalDateTime now = LocalDateTime.now();
            List<EventModel> listUpcoming = new ArrayList<>();
            for (EventModel event: listEvent){
                if (event.getWaktuMulai().isBefore(now.plusWeeks(1)) && event.getWaktuMulai().isAfter(now) && !event.getIsDeleted()){
                    listUpcoming.add(event);
                }
            }
            if (listUpcoming.size() == 0){
                return null;
            }
            return listUpcoming;
        }
    }

    @Override
    public Integer countDone() {
        return eventDb.countEventsWithAllProgressDone();
    }

    @Override
    public Integer countNotDone() {
        return eventDb.countEventsWithAtLeastOneProgressNotDone();
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

    @Override
    public List<EventModel> getListEvent() {
        return eventDb.findAllByIsDeletedIsFalse();
    }

    @Override

    public EventModel getEventById(Long idEvent) {
        Optional<EventModel> eventModel = eventDb.findById(idEvent);
        return eventModel.orElse(null);
    }

    @Override
    public Boolean deleteEvent(Long idEvent) {
        Optional<EventModel> eventModel = eventDb.findById(idEvent);
        EventModel event = eventModel.orElse(null);
        if (event != null){
            if (event.getListProgres().size() == 0){
                event.setIsDeleted(Boolean.TRUE);
                eventDb.save(event);
                return true;
            }
        }
        return false;
    }
}
