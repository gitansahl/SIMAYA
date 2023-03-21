package com.a05.simaya.event.service;

import com.a05.simaya.event.model.ProgresModel;
import com.a05.simaya.event.repository.ProgresDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgresServiceImpl implements ProgresService{
    @Autowired
    ProgresDb progresDb;

    @Override
    public List<ProgresModel> getListProgres(Long idEvent) {
        return progresDb.findAllByEvent_IdEvent(idEvent);
    }

    @Override
    public void addProgres(ProgresModel progres) {
        progresDb.save(progres);
    }

    @Override
    public void updateStatusProgress(ProgresModel progres) {
        progres.setStatus(!progres.getStatus());
        progresDb.save(progres);
    }

    @Override
    public ProgresModel getProgresById(Long idProgres) {
        Optional<ProgresModel> progres = progresDb.findById(idProgres);
        return progres.orElse(null);
    }
}
