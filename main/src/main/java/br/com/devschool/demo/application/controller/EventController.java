package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.model.internal.Event;
import br.com.devschool.demo.domain.model.internal.EventType;
import br.com.devschool.demo.domain.model.internal.dto.EventDTO;
import br.com.devschool.demo.domain.model.internal.dto.ProjectDTO;
import br.com.devschool.demo.domain.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("/event")
    public ResponseEntity<List<EventDTO>> getAllEvents(@RequestParam(required = false) Integer screenId,@RequestParam(required = false) Integer eventTypeId) {
        List<Event> events;
        if (eventTypeId != null){
            events = eventService.getEventByeventTypeId(eventTypeId);
        }else if(screenId != null){
            events = eventService.getEventByscreenId(screenId);
        } else {
            events = eventService.getAllEvents();
        }

        return ResponseEntity.ok(EventDTO.convertList(events));
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Integer id) {
        EventDTO eventDTO = new EventDTO(eventService.getEventById(id));
        return ResponseEntity.ok(eventDTO);
    }

    @PostMapping("/event")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO){
        return ResponseEntity.ok(new EventDTO(eventService.createEvent(eventDTO)));
    }

    @PutMapping("/event/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Integer id,@RequestBody EventDTO eventDTO){
        return ResponseEntity.ok(new EventDTO(eventService.updateEvent(id, eventDTO)));
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity deleteEvent(@PathVariable Integer id) {
        eventService.deleteEventById(id);
        return ResponseEntity.ok().build();
    }
}
