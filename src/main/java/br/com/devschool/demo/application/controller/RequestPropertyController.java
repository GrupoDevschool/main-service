package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.model.internal.RequestProperty;
import br.com.devschool.demo.domain.model.internal.dto.RequestPropertyDTO;
import br.com.devschool.demo.domain.service.RequestPropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class RequestPropertyController {
    private final RequestPropertyService requestPropertyService;

    @Cacheable(value = "getAllRequestProperty")
    @GetMapping("/requestProperty")
    public ResponseEntity<List<RequestProperty>> getAllRequestProperty() {
        return ResponseEntity.ok(requestPropertyService.getAllRequestProperty());
    }

    @Cacheable(value = "getRequestPropertyById")
    @GetMapping("/requestProperty/{id}")
    public ResponseEntity<RequestProperty> getRequestPropertyById(@PathVariable Integer id) {
        return ResponseEntity.ok(requestPropertyService.getRequestPropertyById(id));
    }

    @CacheEvict(value = { "getAllRequestProperty", "getRequestPropertyById" }, allEntries = true)
    @PostMapping("/requestProperty")
    public ResponseEntity<RequestProperty> createRequestProperty(@RequestBody RequestPropertyDTO requestPropertyDTO){
        return ResponseEntity.ok(requestPropertyService.createVRequestProperty(requestPropertyDTO));
    }

    @CacheEvict(value = { "getAllRequestProperty", "getRequestPropertyById" }, allEntries = true)
    @PutMapping("/requestProperty/{id}")
    public ResponseEntity<RequestProperty> updateRequestProperty(@PathVariable Integer id,@RequestBody RequestPropertyDTO requestPropertyDTO){
        return ResponseEntity.ok(requestPropertyService.updateRequestProperty(id, requestPropertyDTO));
    }

    @CacheEvict(value = { "getAllRequestProperty", "getRequestPropertyById" }, allEntries = true)
    @DeleteMapping("/requestProperty/{id}")
    public ResponseEntity deleteRequestProperty(@PathVariable Integer id) {
        requestPropertyService.deleteRequestPropertyById(id);

        return ResponseEntity.ok().build();
    }





}