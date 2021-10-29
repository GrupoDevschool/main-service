package br.com.devschool.demo.application.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.devschool.demo.domain.model.internal.Screen;
import br.com.devschool.demo.domain.model.internal.dto.ScreenDTO;
import br.com.devschool.demo.domain.model.internal.responseDto.ScreenResponseDTO;
import br.com.devschool.demo.domain.service.ScreenService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ScreenController {
    private final ScreenService screenService;

    @GetMapping("/screen")
    public ResponseEntity<List<ScreenResponseDTO>> getAllScreens(@RequestParam Integer versionId,@RequestParam(required = false) Integer screenFatherId ,  @PageableDefault(sort = "order", direction = Sort.Direction.ASC) Pageable pageable ) {
        List<Screen> screens;
        if(screenFatherId != null){
            screens = screenService.getFatherScreenById(screenFatherId, pageable);
        }else {
            screens = screenService.getAllScreens(versionId, pageable);
        }

        return ResponseEntity.ok(ScreenResponseDTO.convertList(screens));
    }

    @GetMapping("/screen/{id}")
    public ResponseEntity<ScreenResponseDTO> getScreenById(@PathVariable Integer id) {
        ScreenResponseDTO screenDTO  = new ScreenResponseDTO(screenService.getScreenById(id));
        return ResponseEntity.ok(screenDTO);
    }

    @PostMapping("/screen")
    public ResponseEntity<ScreenResponseDTO> createScreen(@ModelAttribute ScreenDTO screenDTO){
        ScreenResponseDTO screenResponseDTO = new ScreenResponseDTO(screenService.createScreen(screenDTO)); 
    	return ResponseEntity.ok(screenResponseDTO);
    }

    @PutMapping("/screen/{id}")
    public ResponseEntity<ScreenResponseDTO> updateScreen(@PathVariable Integer id,@RequestBody ScreenDTO screenDTO){
    	ScreenResponseDTO screenResponseDTO = new ScreenResponseDTO(screenService.updateScreen(id, screenDTO));
    	return ResponseEntity.ok(screenResponseDTO);
    }

    @DeleteMapping("/screen/{id}")
    public ResponseEntity deleteScreen(@PathVariable Integer id) {
        screenService.deleteScreenById(id);

        return ResponseEntity.ok().build();
    }
}
