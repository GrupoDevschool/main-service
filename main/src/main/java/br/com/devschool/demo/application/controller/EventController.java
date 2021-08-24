package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.model.internal.Event;
import br.com.devschool.demo.domain.model.internal.dto.EventDTO;
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
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Integer id) {
        return ResponseEntity.ok(eventService.getEventById(id));
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