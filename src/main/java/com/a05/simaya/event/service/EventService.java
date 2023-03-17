package com.a05.simaya.event.service;

import com.a05.simaya.event.model.EventModel;
import com.a05.simaya.event.payload.CreateEventDTO;

import java.util.List;

public interface EventService {
    void tambahEvent(CreateEventDTO eventDTO);


    List<EventModel> getListEvent();

    EventModel getEventById(Long idEvent);

    void deleteEvent(Long idEvent);
}
