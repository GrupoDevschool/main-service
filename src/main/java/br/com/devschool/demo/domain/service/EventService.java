package br.com.devschool.demo.domain.service;

import br.com.devschool.demo.domain.model.internal.Event;
import br.com.devschool.demo.domain.model.internal.Version;
import br.com.devschool.demo.domain.model.internal.dto.EventDTO;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents();

    Event getEventById(Integer id);

    Event createEvent(EventDTO eventDTO);

    Event updateEvent(Integer id, EventDTO eventDTO);

    void deleteEventById(Integer id);
}
