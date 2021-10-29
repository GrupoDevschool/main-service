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
import br.com.devschool.demo.domain.service.StorageService;
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
    private final StorageService storageService;

    @Override
    public List<Screen> getAllScreens(Integer versionId, Pageable pageable) {
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

        String imageUrl = storageService.uploadFile(screenDTO.getImage());
        
        if (Objects.isNull(screenDTO.getScreenFatherId())) {
            Screen newScreen = Screen.builder()
                    .name(screenDTO.getName())
                    .image(imageUrl)
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
                .image(imageUrl)
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
        Optional<Screen> optionalScreen = screenRepository.findById(id);

        if (optionalScreen.isEmpty()) {
            throw new ScreenNotFoundException(id);
        }

        Screen existentScreen = optionalScreen.get();

        Optional<Version> optionalVersion = versionRepository.findById(screenDTO.getVersionId());

        if (optionalVersion.isEmpty()) {
            throw new VersionNotFoundException(screenDTO.getVersionId());
        }

        Version existentVersion = optionalVersion.get();

    	Optional<Version> clonedVersion = (screenDTO.getCloneVersionId() != null)? 
    			versionRepository.findById(screenDTO.getCloneVersionId()) : Optional.empty();        	
        
    	Optional<Screen> screenFather = (screenDTO.getScreenFatherId() != null)? 
    			screenRepository.findById(screenDTO.getScreenFatherId()) : Optional.empty(); 	

        Screen newScreen = Screen.builder()
                .id(id)
                .name(screenDTO.getName())
//                .image(screenDTO.getImage())
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
        if (screenRepository.findById(id).isEmpty()) {
            throw new ScreenNotFoundException(id);
        }

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
}
