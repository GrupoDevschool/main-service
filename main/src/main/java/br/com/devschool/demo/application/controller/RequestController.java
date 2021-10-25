package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.model.internal.Event;
import br.com.devschool.demo.domain.model.internal.Request;
import br.com.devschool.demo.domain.model.internal.dto.EventDTO;
import br.com.devschool.demo.domain.model.internal.dto.RequestDTO;
import br.com.devschool.demo.domain.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @Cacheable(value = "findAllRequests")
    @GetMapping("/request")
    public ResponseEntity<List<RequestDTO>> findAllRequests(@RequestParam(required = false) Integer eventId){
        List<Request> requests;
        if(eventId != null){
            requests = requestService.getEventById(eventId);
        } else {
            requests = requestService.getAllRequests();
        }

        return ResponseEntity.ok(RequestDTO.convertList(requests));
    }

    // show
    @Cacheable(value = "findRequestById")
    @GetMapping("/request/{id}")
    public ResponseEntity<RequestDTO> findRequestById(@PathVariable Integer id){
        RequestDTO requestDTO  = new RequestDTO(requestService.getRequestById(id));
        return ResponseEntity.ok(requestDTO);
    }

    // post
    @CacheEvict(value = { "findAllRequests", "findRequestById" }, allEntries = true)
    @PostMapping("/request")
    public ResponseEntity<RequestDTO> createRequest(@RequestBody RequestDTO request){
        return ResponseEntity.ok(new RequestDTO(requestService.createRequest(request)));
    }

    // put
    @CacheEvict(value = { "findAllRequests", "findRequestById" }, allEntries = true)
    @PutMapping("/request/{id}")
    public ResponseEntity<RequestDTO> updateRequest(@PathVariable Integer id, @RequestBody RequestDTO request) {
        return ResponseEntity.ok(new RequestDTO(requestService.updateRequest(id, request)));
    }

    // delete
    @CacheEvict(value = { "findAllRequests", "findRequestById" }, allEntries = true)
    @DeleteMapping("/request/{id}")
    public ResponseEntity deleteRequest(@PathVariable Integer id) {
        requestService.deleteRequestById(id);
        return ResponseEntity.ok().build();
    }
}

