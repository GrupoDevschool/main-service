package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.model.internal.EventType;
import br.com.devschool.demo.domain.model.internal.dto.EventTypeDTO;
import br.com.devschool.demo.domain.service.EventTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventTypeController {
    private final EventTypeService eventTypeService;

    @Cacheable(value = "findAllEventType")
    @GetMapping("/eventType")
    public ResponseEntity<List<EventTypeDTO>> findAllEventType(){
        return ResponseEntity.ok(EventTypeDTO.convertList(eventTypeService.findAllEventType()));
    }

    // show
    @Cacheable(value = "findEventTypeId")
    @GetMapping("/eventType/{id}")
    public ResponseEntity<EventTypeDTO> findEventTypeId(@PathVariable Integer id){
        return ResponseEntity.ok(new EventTypeDTO(eventTypeService.findEventTypeId(id)));
    }

    // post
    @CacheEvict(value = { "findAllEventType", "findEventTypeId" }, allEntries = true)
    @PostMapping("/eventType")
    public ResponseEntity<EventTypeDTO> createEventType(@RequestBody EventType eventType){
        return ResponseEntity.ok(new EventTypeDTO(eventTypeService.createEventType(eventType)));
    }

    // put
    @CacheEvict(value = { "findAllEventType", "findEventTypeId" }, allEntries = true)
    @PutMapping("/eventType/{id}")
    public ResponseEntity<EventTypeDTO> updateEventType(@PathVariable Integer id, @RequestBody EventType eventType) {
        return ResponseEntity.ok(new EventTypeDTO(eventTypeService.updateEventType(id, eventType)));
    }

    // delete
    @CacheEvict(value = { "findAllEventType", "findEventTypeId" }, allEntries = true)
    @DeleteMapping("/eventType/{id}")
    public ResponseEntity deleteEventTypeId(@PathVariable Integer id) {
        eventTypeService.deleteEventTypeId(id);
        return ResponseEntity.ok().build();
    }
}
