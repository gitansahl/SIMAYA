package com.a05.simaya.event.service;

import com.a05.simaya.event.model.EventModel;
import com.a05.simaya.event.payload.CreateEventDTO;

public interface EventService {
    void tambahEvent(CreateEventDTO eventDTO);
    EventModel getEventById(Long idEvent);

    void deleteEvent(Long idEvent);
}
