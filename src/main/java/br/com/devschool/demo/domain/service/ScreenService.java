package br.com.devschool.demo.domain.service;

import br.com.devschool.demo.domain.model.internal.Screen;
import br.com.devschool.demo.domain.model.internal.dto.ScreenDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScreenService {
    List<Screen> getAllScreens(Integer versionId, Pageable pageable);

    Screen getScreenById(Integer id);

    Screen createScreen(ScreenDTO screenDTO);

    Screen updateScreen(Integer id, ScreenDTO screenDTO);

    void deleteScreenById(Integer id);
}
