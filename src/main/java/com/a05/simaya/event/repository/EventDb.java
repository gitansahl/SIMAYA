package com.a05.simaya.event.repository;

import com.a05.simaya.event.model.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDb extends JpaRepository<EventModel, Long> {
}
