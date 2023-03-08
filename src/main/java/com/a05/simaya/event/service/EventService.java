package com.a05.simaya.event.service;

import com.a05.simaya.event.payload.CreateEventDTO;

public interface EventService {
    void tambahEvent(CreateEventDTO eventDTO);
}
