package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.model.internal.EventType;
import br.com.devschool.demo.domain.service.EventTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventTypeController {
    private final EventTypeService eventTypeService;

    @Cacheable(value = "findAllEventType")
    @GetMapping("/eventType")
    public ResponseEntity<List<EventType>> findAllEventType(){
        return ResponseEntity.ok(eventTypeService.findAllEventType());
    }

    // show
    @Cacheable(value = "findEventTypeId")
    @GetMapping("/eventType/{id}")
    public ResponseEntity<EventType> findEventTypeId(@PathVariable Integer id){
        return ResponseEntity.ok(eventTypeService.findEventTypeId(id));
    }

    // post
    @CacheEvict(value = { "findAllEventType", "findEventTypeId" }, allEntries = true)
    @PostMapping("/eventType")
    public ResponseEntity<EventType> createEventType(@RequestBody EventType eventType){
        return ResponseEntity.ok(eventTypeService.createEventType(eventType));
    }

    // put
    @CacheEvict(value = { "findAllEventType", "findEventTypeId" }, allEntries = true)
    @PutMapping("/eventType/{id}")
    public ResponseEntity<EventType> updateEventType(@PathVariable Integer id, @RequestBody EventType eventType) {
        return ResponseEntity.ok(eventTypeService.updateEventType(id, eventType));
    }

    // delete
    @CacheEvict(value = { "findAllEventType", "findEventTypeId" }, allEntries = true)
    @DeleteMapping("/eventType/{id}")
    public ResponseEntity deleteEventTypeId(@PathVariable Integer id) {
        eventTypeService.deleteEventTypeId(id);
        return ResponseEntity.ok().build();
    }
}
