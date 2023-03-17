package com.a05.simaya.event.repository;

import com.a05.simaya.event.model.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventDb extends JpaRepository<EventModel, Long> {
    List<EventModel> findAllByIsDeletedIsFalse();
}
