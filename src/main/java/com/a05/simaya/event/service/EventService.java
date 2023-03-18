package com.a05.simaya.event.service;

import com.a05.simaya.event.model.EventModel;
import com.a05.simaya.event.payload.CreateEventDTO;

import java.util.List;

public interface EventService {
    void tambahEvent(CreateEventDTO eventDTO);

    EventModel getEventById(Long idEvent);

    List<EventModel> getListOngoing();
    List<EventModel> getListUpcoming();
    Integer countDone();
    Integer countNotDone();

    List<EventModel> getListEvent();

    Boolean deleteEvent(Long idEvent);
}
