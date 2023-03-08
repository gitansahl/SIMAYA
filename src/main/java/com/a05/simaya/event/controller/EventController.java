package com.a05.simaya.event.controller;


import com.a05.simaya.anggota.model.AnggotaModel;
import com.a05.simaya.anggota.payload.CreateAnggotaDTO;
import com.a05.simaya.anggota.service.AnggotaService;
import com.a05.simaya.event.payload.CreateEventDTO;
import com.a05.simaya.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EventController {

    @Autowired
    private AnggotaService anggotaService;

    @Autowired
    private EventService eventService;

    @GetMapping(value = "/tambah-event")
    public String getPage(Model model) {
        CreateEventDTO event = new CreateEventDTO();

        List<AnggotaModel> listAnggota = anggotaService.getListAnggota();

        model.addAttribute("listAnggota", listAnggota);
        model.addAttribute("event", event);
        return "event/form-tambah-event";
    }

    @PostMapping(value = "/tambah-event")
    public String postForm(CreateEventDTO event) {
        eventService.tambahEvent(event);
        return "hello_world";
    }
}
