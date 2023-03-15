package com.a05.simaya.event.repository;
import com.a05.simaya.event.model.ProgresModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgresDb extends JpaRepository<ProgresModel, Long> {

    List<ProgresModel> findAllByEvent_IdEvent(Long event_idEvent);
}
