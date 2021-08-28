package br.com.devschool.demo.application.controller;

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
    public ResponseEntity<List<RequestDTO>> findAllRequests(){
        return ResponseEntity.ok(RequestDTO.convertList(requestService.getAllRequests()));
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
    public ResponseEntity<Request> createRequest(@RequestBody RequestDTO request){
        return ResponseEntity.ok(requestService.createRequest(request));
    }

    // put
    @CacheEvict(value = { "findAllRequests", "findRequestById" }, allEntries = true)
    @PutMapping("/request/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable Integer id, @RequestBody RequestDTO request) {
        return ResponseEntity.ok(requestService.updateRequest(id, request));
    }

    // delete
    @CacheEvict(value = { "findAllRequests", "findRequestById" }, allEntries = true)
    @DeleteMapping("/request/{id}")
    public ResponseEntity deleteRequest(@PathVariable Integer id) {
        requestService.deleteRequestById(id);
        return ResponseEntity.ok().build();
    }
}

