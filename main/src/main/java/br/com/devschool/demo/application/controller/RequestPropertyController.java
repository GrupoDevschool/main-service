package br.com.devschool.demo.application.controller;

import java.util.List;

import br.com.devschool.demo.domain.model.internal.Event;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.devschool.demo.domain.model.internal.RequestProperty;
import br.com.devschool.demo.domain.model.internal.dto.RequestPropertyDTO;
import br.com.devschool.demo.domain.service.RequestPropertyService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping({"/requestProperty"})
@RequiredArgsConstructor
public class RequestPropertyController {
    private final RequestPropertyService requestPropertyService;

    @Cacheable(value = "getAllRequestProperty")
    @GetMapping
    public ResponseEntity<List<RequestPropertyDTO>> getAllRequestProperty(@RequestParam(required = false) Integer id) {
        List<RequestProperty> requestProperties;
        if(id != null){
            requestProperties = requestPropertyService.getByRequestId(id);
        } else {
            requestProperties = requestPropertyService.getAllRequestProperty();
        }

        return ResponseEntity.ok(RequestPropertyDTO.covertList(requestPropertyService.getAllRequestProperty()));
    }

    @Cacheable(value = "getRequestPropertyById")
    @GetMapping("/requestProperty/{id}")
    public ResponseEntity<RequestPropertyDTO> getRequestPropertyById(@PathVariable Integer id) {
        return ResponseEntity.ok(new RequestPropertyDTO(requestPropertyService.getRequestPropertyById(id)));
    }

    @CacheEvict(value = { "getAllRequestProperty", "getRequestPropertyById" }, allEntries = true)
    @PostMapping("/requestProperty")
    public ResponseEntity<RequestPropertyDTO> createRequestProperty(@RequestBody RequestPropertyDTO requestPropertyDTO){
        return ResponseEntity.ok(new RequestPropertyDTO(requestPropertyService.createVRequestProperty(requestPropertyDTO)));
    }

    @CacheEvict(value = { "getAllRequestProperty", "getRequestPropertyById" }, allEntries = true)
    @PutMapping("/requestProperty/{id}")
    public ResponseEntity<RequestPropertyDTO> updateRequestProperty(@PathVariable Integer id,@RequestBody RequestPropertyDTO requestPropertyDTO){
        return ResponseEntity.ok(new RequestPropertyDTO(requestPropertyService.updateRequestProperty(id, requestPropertyDTO)));
    }

    @CacheEvict(value = { "getAllRequestProperty", "getRequestPropertyById" }, allEntries = true)
    @DeleteMapping("/requestProperty/{id}")
    public ResponseEntity deleteRequestProperty(@PathVariable Integer id) {
        requestPropertyService.deleteRequestPropertyById(id);

        return ResponseEntity.ok().build();
    }





}
