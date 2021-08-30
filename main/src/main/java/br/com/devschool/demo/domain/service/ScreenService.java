package br.com.devschool.demo.domain.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import br.com.devschool.demo.domain.model.internal.Screen;
import br.com.devschool.demo.domain.model.internal.dto.ScreenDTO;

public interface ScreenService {
    List<Screen> getAllScreens(Integer versionId, Pageable pageable);

    Screen getScreenById(Integer id);

    Screen createScreen(ScreenDTO screenDTO);

    Screen updateScreen(Integer id, ScreenDTO screenDTO);

    void deleteScreenById(Integer id);
}
