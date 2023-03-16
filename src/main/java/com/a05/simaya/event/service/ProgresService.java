package com.a05.simaya.event.service;

import com.a05.simaya.event.model.ProgresModel;

import java.util.List;

public interface ProgresService {
    List<ProgresModel> getListProgres(Long idEvent);

    void addProgres(ProgresModel progres);

    void updateStatusProgress(ProgresModel progres);

    ProgresModel getProgresById(Long idProgres);
}
