package com.a05.simaya.event.controller;

import com.a05.simaya.anggota.model.AnggotaModel;
import com.a05.simaya.anggota.service.AnggotaService;
import com.a05.simaya.event.model.EventModel;
import com.a05.simaya.event.model.ProgresModel;
import com.a05.simaya.event.payload.CreateProgresDTO;
import com.a05.simaya.event.service.EventService;
import com.a05.simaya.event.service.ProgresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ProgresRestController {

    @Autowired
    private ProgresService progresService;

    @Autowired
    private EventService eventService;

    @GetMapping(value = "/progres/view/{id}")
    private ResponseEntity getAllAnggota(
            @PathVariable(value="id") Long idEvent
    ) {
        ResponseEntity responseEntity = null;
        try {
            List<ProgresModel> listProgres = progresService.getListProgres(idEvent);
            responseEntity = ResponseEntity.ok(listProgres);
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;

    }

    @PostMapping(value = "/progres/save")
    public ResponseEntity addProgress(@RequestBody CreateProgresDTO progresDTO) {
//        log.info("api add book {}", bookModel);
        ResponseEntity responseEntity = null;

        try {
            EventModel event  = eventService.getEventById(progresDTO.getIdEvent());
            ProgresModel progresModel = new ProgresModel();
            progresModel.setNama(progresDTO.getNama());
            progresModel.setEvent(event);

            progresService.addProgres(progresModel);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
//            log.error("Error in add book!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @PutMapping(value = "/progres/update/{idProgress}")
    public ResponseEntity updateProgress(
            @PathVariable(value = "idProgress") Long idProgres
    ){
        ResponseEntity responseEntity = null;

        try {
            ProgresModel progres = progresService.getProgresById(idProgres);
            progresService.updateStatusProgress(progres);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
//            log.error("Error in add book!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

}
