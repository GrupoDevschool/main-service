package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.model.internal.Screen;
import br.com.devschool.demo.domain.model.internal.dto.ScreenDTO;
import br.com.devschool.demo.domain.service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScreenController {
    private final ScreenService screenService;

    @GetMapping("/screen")
    public ResponseEntity<List<Screen>> getAllScreens(@RequestParam Integer versionId, @PageableDefault(sort = "order", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(screenService.getAllScreens(versionId, pageable));
    }

    @GetMapping("/screen/{id}")
    public ResponseEntity<Screen> getScreenById(@PathVariable Integer id) {
        return ResponseEntity.ok(screenService.getScreenById(id));
    }

    @PostMapping("/screen")
    public ResponseEntity<Screen> createScreen(@RequestBody ScreenDTO screenDTO){
        return ResponseEntity.ok(screenService.createScreen(screenDTO));
    }

    @PutMapping("/screen/{id}")
    public ResponseEntity<Screen> updateScreen(@PathVariable Integer id,@RequestBody ScreenDTO screenDTO){
        return ResponseEntity.ok(screenService.updateScreen(id, screenDTO));
    }

    @DeleteMapping("/screen/{id}")
    public ResponseEntity deleteScreen(@PathVariable Integer id) {
        screenService.deleteScreenById(id);

        return ResponseEntity.ok().build();
    }
}
