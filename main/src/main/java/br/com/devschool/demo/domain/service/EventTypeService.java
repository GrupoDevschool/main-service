package br.com.devschool.demo.domain.service;


import br.com.devschool.demo.domain.model.internal.EventType;

import java.util.List;

public interface EventTypeService {

    List<EventType> findAllEventType();

    EventType findEventTypeId(Integer id);

    EventType createEventType(EventType eventType);

    EventType updateEventType(Integer id, EventType eventType);

    void deleteEventTypeId(Integer id);

}