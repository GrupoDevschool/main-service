package br.com.devschool.demo.domain.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.devschool.demo.domain.model.internal.Screen;
import br.com.devschool.demo.domain.model.internal.Version;
import br.com.devschool.demo.domain.model.internal.dto.ScreenDTO;
import br.com.devschool.demo.domain.service.ScreenService;
import br.com.devschool.demo.infra.exception.CascadeDeletionException;
import br.com.devschool.demo.infra.exception.ScreenNotFoundException;
import br.com.devschool.demo.infra.exception.ScreensNotListedException;
import br.com.devschool.demo.infra.exception.VersionNotFoundException;
import br.com.devschool.demo.infra.repository.EventRepository;
import br.com.devschool.demo.infra.repository.ScreenRepository;
import br.com.devschool.demo.infra.repository.VersionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ScreenServiceImpl implements ScreenService {
    private final VersionRepository versionRepository;
    private final ScreenRepository screenRepository;
    private final EventRepository eventRepository;

    @Override
    public List<Screen> getAllScreens() {
        List<Screen> screens = screenRepository.findAll();

        if (screens.isEmpty()) {
            throw new ScreensNotListedException();
        }

        return screens;
    }

    @Override
    public List<Screen> getAllScreensByVersion(Integer versionId, Pageable pageable) {
        List<Screen> screens = screenRepository.findAllByVersion_Id(versionId, pageable);

        if (screens.isEmpty()) {
            throw new ScreensNotListedException();
        }

        return screens;
    }

    @Override
    public Screen getScreenById(Integer id) {
        return screenRepository.findById(id).orElseThrow(() -> new ScreenNotFoundException(id));
    }

    @Override
    public Screen createScreen(ScreenDTO screenDTO) {
        Optional<Version> optionalVersion = versionRepository.findById(screenDTO.getVersionId());

        if (optionalVersion.isEmpty()) {
            throw new VersionNotFoundException(screenDTO.getVersionId());
        }

        Version existentVersion = optionalVersion.get();
        
        if (Objects.isNull(screenDTO.getScreenFatherId())) {
            Screen newScreen = Screen.builder()
                    .name(screenDTO.getName())
                    .image(screenDTO.getImage())
                    .active(screenDTO.isActive())
                    .order(screenDTO.getOrder())
                    .urlog(screenDTO.getUrlog())
                    .version(existentVersion)
                    .createdDate(LocalDate.now())
                    .updatedDate(LocalDate.now())
                    .build();

            return screenRepository.save(newScreen);
        }

        Optional<Screen> screenFatherOptional = screenRepository.findById(screenDTO.getScreenFatherId());

        if (screenFatherOptional.isEmpty()) {
            throw new ScreenNotFoundException(screenDTO.getScreenFatherId());
        }

        Screen newScreen = Screen.builder()
                .name(screenDTO.getName())
                .image(screenDTO.getImage())
                .active(screenDTO.isActive())
                .order(screenDTO.getOrder())
                .urlog(screenDTO.getUrlog())
                .version(existentVersion)
                .screenFather(screenFatherOptional.get())
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        return screenRepository.save(newScreen);
    }

    @Override
    public Screen updateScreen(Integer id, ScreenDTO screenDTO) {

    	Screen existentScreen = screenRepository.findById(id).orElseThrow(() -> new ScreenNotFoundException(id));

    	Version existentVersion = versionRepository.findById(screenDTO.getVersionId())
    			.orElseThrow(() -> new VersionNotFoundException(screenDTO.getVersionId()));

    	Optional<Version> clonedVersion = (screenDTO.getCloneVersionId() != null)? 
    			versionRepository.findById(screenDTO.getCloneVersionId()) : Optional.empty();        	
        
    	Optional<Screen> screenFather = (screenDTO.getScreenFatherId() != null)? 
    			screenRepository.findById(screenDTO.getScreenFatherId()) : Optional.empty(); 	
    	
        Screen newScreen = Screen.builder()
                .id(id)
                .name(screenDTO.getName())
                .image(screenDTO.getImage())
                .active(screenDTO.isActive())
                .order(screenDTO.getOrder())
                .urlog(screenDTO.getUrlog())
                .version(existentVersion)
                .cloneVersion((clonedVersion.isEmpty())? null : clonedVersion.get())
                .screenFather((screenFather.isEmpty())? null : screenFather.get())
                .createdDate(existentScreen.getCreatedDate())
                .updatedDate(LocalDate.now())
                .build();

        return screenRepository.save(newScreen);
    }

    @Override
    public void deleteScreenById(Integer id) {
    	screenRepository.findById(id).orElseThrow(() -> new ScreenNotFoundException(id));

        if (!eventRepository.findAllByScreen_Id(id).isEmpty()) {
            throw new CascadeDeletionException("Essa Tela não pode ser excluída pois já possui eventos cadastrados para ela.");
        }

        if (!screenRepository.findAllByscreenFather_Id(id).isEmpty()) {
            throw new CascadeDeletionException("Essa Tela não pode ser excluída pois já possui telas associadas a ela.");
        }
        
        screenRepository.deleteById(id);
    }



    @Override
    public List<Screen> getFatherScreenById(Integer screenFatherId, Pageable pageable) {
        return screenRepository.findAllByscreenFather_Id(screenFatherId , pageable);
    }

	@Override
	public List<Screen> getSisters(Integer screenId) {
		Screen screen = screenRepository.findById(screenId).orElseThrow(() -> new ScreenNotFoundException(screenId));
		if (screen.getScreenFather() == null) return List.of();
		List<Screen> sistersScreens = screenRepository.findAllByscreenFather_Id(screen.getScreenFather().getId());
		return sistersScreens;
	}
}
