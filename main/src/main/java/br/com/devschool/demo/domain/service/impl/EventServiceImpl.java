package br.com.devschool.demo.domain.service.impl;

import br.com.devschool.demo.domain.model.internal.*;
import br.com.devschool.demo.domain.model.internal.dto.EventDTO;
import br.com.devschool.demo.domain.service.EventService;
import br.com.devschool.demo.infra.exception.*;
import br.com.devschool.demo.infra.repository.EventRepository;
import br.com.devschool.demo.infra.repository.EventTypeRepository;
import br.com.devschool.demo.infra.repository.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ScreenRepository screenRepository;
    private final EventTypeRepository eventTypeRepository;


    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> getEventByeventTypeId(Integer eventTypeId) {
        return eventRepository.findAllByeventType_Id(eventTypeId);
    }
    @Override
    public Event getEventById(Integer id) {
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
    }

    @Override
    public List<Event> getEventByscreenId(Integer screenId) {
        return eventRepository.findAllByScreen_Id(screenId);
    }

    @Override
    public Event createEvent(EventDTO eventDTO) {
        Optional<EventType> optionalEventType = eventTypeRepository.findById(eventDTO.getEventTypeId());
        Optional<Screen> optionalScreen = screenRepository.findById(eventDTO.getScreenId());

        if (optionalEventType.isEmpty()) {
            throw new EventTypeNotFoundException(eventDTO.getEventTypeId());
        }

        if (optionalScreen.isEmpty()) {
            throw new ScreenNotFoundException(eventDTO.getScreenId());
        }

        Event newEvent = Event.builder()
                .active(eventDTO.isActive())
                .order(eventDTO.getOrder())
                .parameter(eventDTO.getParameter())
                .screen(optionalScreen.get())
                .eventType(optionalEventType.get())
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        return eventRepository.save(newEvent);
    }

    @Override
    public Event updateEvent(Integer id, EventDTO eventDTO) {
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException(id);
        }

        Event existentEvent = optionalEvent.get();

        Optional<EventType> optionalEventType = eventTypeRepository.findById(eventDTO.getEventTypeId());
        Optional <Screen> optionalScreen = screenRepository.findById(eventDTO.getScreenId());

        if (optionalEventType.isEmpty()) {
            throw new EventTypeNotFoundException(eventDTO.getEventTypeId());
        }

        if (optionalScreen.isEmpty()) {
            throw new ScreenNotFoundException(eventDTO.getScreenId());
        }

        Event updatedEvent = Event.builder()
                .id(id)
                .active(eventDTO.isActive())
                .order(eventDTO.getOrder())
                .parameter(eventDTO.getParameter())
                .screen(optionalScreen.get())
                .eventType(optionalEventType.get())
                .createdDate(existentEvent.getCreatedDate())
                .updatedDate(LocalDate.now())
                .build();

        return eventRepository.save(updatedEvent);
    }


    @Override
    public void deleteEventById(Integer id) {
        if (eventRepository.findById(id).isEmpty()) {
            throw new EventTypeNotFoundException(id);
        }

        eventRepository.deleteById(id);
    }


}

