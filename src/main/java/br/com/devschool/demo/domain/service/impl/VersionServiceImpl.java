package br.com.devschool.demo.domain.service.impl;

import br.com.devschool.demo.domain.model.internal.*;
import br.com.devschool.demo.domain.model.internal.dto.VersionDTO;
import br.com.devschool.demo.domain.service.VersionService;
import br.com.devschool.demo.infra.exception.CascadeDeletionException;
import br.com.devschool.demo.infra.exception.ProjectNotFoundException;
import br.com.devschool.demo.infra.exception.VersionNotFoundException;
import br.com.devschool.demo.infra.exception.VersionsNotListedException;
import br.com.devschool.demo.infra.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VersionServiceImpl implements VersionService {
    private final VersionRepository versionRepository;
    private final ProjectRepository projectRepository;
    private final ScreenRepository screenRepository;
    private final EventRepository eventRepository;
    private final EventTypeRepository eventTypeRepository;
    private final RequestRepository requestRepository;
    private final RequestPropertyRepository requestPropertyRepository;

    @Override
    public List<Version> getAllVersions(Integer projectId, Pageable pageable) {
        List<Version> versions = versionRepository.findAllByProject_id(projectId, pageable);

        if (versions.isEmpty()) {
            throw new VersionsNotListedException();
        }

        return versions;
    }

    public Version getVersionById(Integer id) {
        return versionRepository.findById(id).orElseThrow(() -> new VersionNotFoundException(id));
    }

    @Override
    public Version createVersion(VersionDTO versionDTO) {
        Optional<Project> optionalProject = projectRepository.findById(versionDTO.getProjectId());

        if (optionalProject.isEmpty()) {
            throw new ProjectNotFoundException(versionDTO.getProjectId());
        }

        Project existentProject = optionalProject.get();

        Version newVersion = Version.builder()
                .versionNumber(versionDTO.getVersionNumber())
                .gmud(versionDTO.getGmud())
                .description(versionDTO.getDescription())
                .deployDate(versionDTO.getDeployDate())
                .status(versionDTO.getStatus())
                .order(versionDTO.getOrder())
                .project(existentProject)
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        Version createdVersion = versionRepository.save(newVersion);

        if (!Objects.isNull(versionDTO.getVersionCloneId())) {
            createdVersion.setVersionCloneId(versionDTO.getVersionCloneId());
            versionRepository.save(createdVersion);
            cloneScreens(createdVersion, versionDTO.getVersionCloneId());
        }

        return createdVersion;
    }

    @Override
    public Version updateVersion(Integer id, VersionDTO versionDTO) {
        Optional<Version> optionalVersion = versionRepository.findById(id);

        if (optionalVersion.isEmpty()) {
            throw new VersionNotFoundException(id);
        }

        Version existentVersion = optionalVersion.get();

        Optional<Project> optionalProject = projectRepository.findById(versionDTO.getProjectId());

        if (optionalProject.isEmpty()) {
            throw new ProjectNotFoundException(versionDTO.getProjectId());
        }

        Project existentProject = optionalProject.get();

        Version updatedVersion = Version.builder()
                .id(id)
                .versionNumber(versionDTO.getVersionNumber())
                .gmud(versionDTO.getGmud())
                .description(versionDTO.getDescription())
                .deployDate(versionDTO.getDeployDate())
                .status(versionDTO.getStatus())
                .order(versionDTO.getOrder())
                .project(existentProject)
                .createdDate(existentVersion.getCreatedDate())
                .updatedDate(LocalDate.now())
                .build();

        return versionRepository.save(updatedVersion);
    }

    @Override
    public void deleteVersionById(Integer id) {
        if (versionRepository.findById(id).isEmpty()) {
            throw new VersionNotFoundException(id);
        }

        if (!screenRepository.findByVersion_Id(id).isEmpty()) {
            throw new CascadeDeletionException("Essa Versão de Projeto não pode ser excluída pois já possui telas cadastradas.");
        }

        versionRepository.deleteById(id);
    }

    public void cloneScreens(Version createdVersion, Integer originalVersionId) {
        Optional<Version> clonedVersion = versionRepository.findById(originalVersionId);

        if (clonedVersion.isEmpty()) {
            throw new VersionNotFoundException(originalVersionId);
        }

        List<Screen> screens = screenRepository.findByVersion_Id(originalVersionId);
        List<Screen> newScreens = new ArrayList<>();

        for(Screen screen : screens) {
            Screen screenCloned = Screen.builder()
                    .version(createdVersion)
                    .name(screen.getName())
                    .image(screen.getImage())
                    .active(screen.isActive())
                    .order(screen.getOrder())
                    .urlog(screen.getUrlog())
                    .createdDate(screen.getCreatedDate())
                    .updatedDate(screen.getUpdatedDate())
                    .build();

            Screen newScreen = screenRepository.save(screenCloned);

            newScreens.add(newScreen);

            cloneEvents(newScreen, screen.getId());
        }

        int i = 0;
        for (Screen screen: screens) {
            if (!Objects.isNull(screen.getScreenFather())) {
                int screenFatherIndex = screens.indexOf(screen.getScreenFather());

                Screen updatedScreen = newScreens.get(i);

                updatedScreen.setScreenFather(newScreens.get(screenFatherIndex));

                screenRepository.save(updatedScreen);
            }
            i++;
        }
    }

    public void cloneEvents(Screen createdScreen, Integer originalScreenId) {
        List<Event> events = eventRepository.findAllByScreen_Id(originalScreenId);

        for (Event event : events) {
            Event createdEvent = Event.builder()
                    .screen(createdScreen)
                    .eventType(event.getEventType())
                    .active(event.isActive())
                    .order(event.getOrder())
                    .parameter(event.getParameter())
                    .createdDate(event.getCreatedDate())
                    .updatedDate(event.getUpdatedDate())
                    .build();

            Event newEvent = eventRepository.save(createdEvent);

            cloneRequests(newEvent, event.getId());
        }
    }

    public void cloneRequests(Event createdEvent, Integer originalEventId) {
        List<Request> requests = requestRepository.getAllByEvent_Id(originalEventId);

        List<Request> newRequests = new ArrayList<>();

        for (Request request : requests) {
            Request createdRequest = Request.builder()
                    .URL_Homolog(request.getURL_Homolog())
                    .event(createdEvent)
                    .URI_Prod(request.getURI_Prod())
                    .Description(request.getDescription())
                    .Layer(request.getLayer())
                    .status(request.isStatus())
                    .order(request.getOrder())
                    .createdDate(request.getCreatedDate())
                    .updatedDate(request.getUpdatedDate())
                    .build();

            Request newRequest = requestRepository.save(createdRequest);

            newRequests.add(newRequest);

            cloneProperties(newRequest, request.getId());
        }

        int i = 0;
        for (Request request: requests) {
            if (!Objects.isNull(request.getRequestFather())) {
                int requestFatherIndex = requests.indexOf(request.getRequestFather());

                Request updatedRequest = newRequests.get(i);

                updatedRequest.setRequestFather(newRequests.get(requestFatherIndex));

                requestRepository.save(updatedRequest);
            }
            i++;
        }


    }

    public void cloneProperties(Request createdRequest, Integer originalPropertyId) {
        List<RequestProperty> requestProperties = requestPropertyRepository.findAllByRequest_Id(originalPropertyId);

        for (RequestProperty requests : requestProperties) {
            RequestProperty createdRequestProperty = RequestProperty.builder()
                    .request(createdRequest)
                    .type(requests.getType())
                    .key(requests.getKey())
                    .value(requests.getValue())
                    .order(requests.getOrder())
                    .createdDate(requests.getCreatedDate())
                    .updatedDate(requests.getUpdatedDate())
                    .build();

            requestPropertyRepository.save(createdRequestProperty);
        }
    }
}
