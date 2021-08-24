package br.com.devschool.demo.domain.service.impl;

import br.com.devschool.demo.domain.model.internal.EventType;
import br.com.devschool.demo.domain.service.EventTypeService;
import br.com.devschool.demo.infra.exception.EventTypeNotFoundException;
import br.com.devschool.demo.infra.repository.EventTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventTypeServiceImpl implements EventTypeService {
    private final EventTypeRepository eventTypeRepository;


    @Override
    public List<EventType> findAllEventType() {
        return eventTypeRepository.findAll();
    }

    @Override
    public EventType findEventTypeId (Integer id) {
        return eventTypeRepository.findById(id).orElseThrow(() -> new EventTypeNotFoundException(id));
    }

    @Override
    public EventType createEventType(EventType eventType) {
        EventType newEventType = EventType.builder()
                .name(eventType.getName())
                .status(eventType.isStatus())
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        return eventTypeRepository.save(newEventType);
    }

    @Override
    public EventType updateEventType(Integer id, EventType eventType) {
        Optional<EventType> eventTypeOptional = eventTypeRepository.findById(id);

        if (eventTypeOptional.isEmpty()) {
            throw new EventTypeNotFoundException(id);
        }

        EventType eventTypeExistent = eventTypeOptional.get();

        EventType updatedEventType = EventType.builder()
                .id(eventTypeExistent.getId())
                .name(eventType.getName())
                .status(eventType.isStatus())
                .updatedDate(LocalDate.now())
                .createdDate(eventTypeExistent.getCreatedDate())
                .build();

        return eventTypeRepository.save(updatedEventType);

    }

    @Override
    public void deleteEventTypeId(Integer id) {
        if (eventTypeRepository.findById(id).isEmpty()) {
            throw new EventTypeNotFoundException(id);
        }

        eventTypeRepository.deleteById(id);
    }
}
