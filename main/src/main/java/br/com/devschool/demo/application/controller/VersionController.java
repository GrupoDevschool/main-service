package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.model.internal.Version;
import br.com.devschool.demo.domain.model.internal.dto.ScreenDTO;
import br.com.devschool.demo.domain.model.internal.dto.VersionDTO;
import br.com.devschool.demo.domain.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class VersionController {
    private final VersionService versionService;

    @GetMapping("/version")
    public ResponseEntity<List<VersionDTO>> getAllVersions(@RequestParam Integer projectId, @PageableDefault(sort = {"versionNumber", "order"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(VersionDTO.convertList(versionService.getAllVersions(projectId, pageable)));
    }

    @GetMapping("/version/{id}")
    public ResponseEntity<VersionDTO> getVersionById(@PathVariable Integer id) {
        VersionDTO versionDTO  = new VersionDTO(versionService.getVersionById(id));
        return ResponseEntity.ok(versionDTO);
    }

    @PostMapping("/version")
    public ResponseEntity<Version> createVersion(@RequestBody VersionDTO versionDTO){
        return ResponseEntity.ok(versionService.createVersion(versionDTO));
    }

    @PutMapping("/version/{id}")
    public ResponseEntity<Version> updateVersion(@PathVariable Integer id,@RequestBody VersionDTO versionDTO){
        return ResponseEntity.ok(versionService.updateVersion(id, versionDTO));
    }

    @DeleteMapping("/version/{id}")
    public ResponseEntity deleteVersion(@PathVariable Integer id) {
        versionService.deleteVersionById(id);

        return ResponseEntity.ok().build();
    }





}
