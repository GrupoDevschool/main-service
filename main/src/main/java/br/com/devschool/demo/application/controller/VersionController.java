package br.com.devschool.demo.application.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.devschool.demo.domain.model.internal.dto.VersionDTO;
import br.com.devschool.demo.domain.model.internal.responseDto.VersionResponseDTO;
import br.com.devschool.demo.domain.service.VersionService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class VersionController {
    private final VersionService versionService;

    @GetMapping("/version")
    public ResponseEntity<List<VersionResponseDTO>> getAllVersions(@RequestParam Integer projectId, @PageableDefault(sort = {"versionNumber", "order"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(VersionResponseDTO.convertList(versionService.getAllVersions(projectId, pageable)));
    }

    @GetMapping("/version/{id}")
    public ResponseEntity<VersionResponseDTO> getVersionById(@PathVariable Integer id) {
        VersionResponseDTO versionResponseDTO  = new VersionResponseDTO(versionService.getVersionById(id));
        return ResponseEntity.ok(versionResponseDTO);
    }

    @PostMapping("/version")
    public ResponseEntity<VersionResponseDTO> createVersion(@RequestBody VersionDTO versionDTO){
    	VersionResponseDTO versionResponseDTO = new VersionResponseDTO(versionService.createVersion(versionDTO));
    	return ResponseEntity.ok(versionResponseDTO);
    }

    @PutMapping("/version/{id}")
    public ResponseEntity<VersionResponseDTO> updateVersion(@PathVariable Integer id,@RequestBody VersionDTO versionDTO){
        VersionResponseDTO versionResponseDTO = new VersionResponseDTO(versionService.updateVersion(id, versionDTO)); 
    	return ResponseEntity.ok(versionResponseDTO);
    }

    @DeleteMapping("/version/{id}")
    public ResponseEntity deleteVersion(@PathVariable Integer id) {
        versionService.deleteVersionById(id);

        return ResponseEntity.ok().build();
    }





}
