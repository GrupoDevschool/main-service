package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.model.internal.Event;
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
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(EventDTO.convertList(eventService.getAllEvents()));
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Integer id) {
        EventDTO eventDTO = new EventDTO(eventService.getEventById(id));
        return ResponseEntity.ok(eventDTO);
    }

    @PostMapping("/event")
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO eventDTO){
        return ResponseEntity.ok(eventService.createEvent(eventDTO));
    }

    @PutMapping("/event/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Integer id,@RequestBody EventDTO eventDTO){
        return ResponseEntity.ok(eventService.updateEvent(id, eventDTO));
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity deleteEvent(@PathVariable Integer id) {
        eventService.deleteEventById(id);
        return ResponseEntity.ok().build();
    }
}
