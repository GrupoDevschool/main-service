package br.com.devschool.demo.domain.service.impl;

import br.com.devschool.demo.domain.model.internal.Project;
import br.com.devschool.demo.domain.service.ProjectService;
import br.com.devschool.demo.infra.exception.CascadeDeletionException;
import br.com.devschool.demo.infra.exception.ProjectNotFoundException;
import br.com.devschool.demo.infra.exception.ProjectsNotListedException;
import br.com.devschool.demo.infra.repository.ProjectRepository;
import br.com.devschool.demo.infra.repository.VersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final VersionRepository versionRepository;

    @Override
    public List<Project> getAllProjects(String name, String status, Pageable pageable) {
        List<Project> projects;

        if (Objects.isNull(status)) {
            projects = projectRepository.findAllByNameStartingWith(name,pageable);

        } else {
            projects = projectRepository.findAllByNameStartingWithAndStatus(name, Boolean.parseBoolean(status), pageable);
        }

        if (projects.isEmpty()) {
            throw new ProjectsNotListedException();
        }

//        List<ProjectDTO> projectDTOS = projects.stream().map()

        return projects;
    }

    @Override
    public Project getProjectById(Integer id){
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    }

    @Override
    public Project createProject(Project project) {
        Project newProject = Project.builder()
                .name(project.getName())
                .status(project.isStatus())
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        return projectRepository.save(newProject);
    }

    @Override
    @Transactional
    public Project updateProject(Integer id, Project project) {
        Optional<Project> projectOptional = projectRepository.findById(id);

        if (projectOptional.isEmpty()) {
            throw new ProjectNotFoundException(id);
        }

        Project projectExistent = projectOptional.get();

        Project updatedProject = Project.builder()
                .id(projectExistent.getId())
                .name(project.getName())
                .status(project.isStatus())
                .updatedDate(LocalDate.now())
                .createdDate(projectExistent.getCreatedDate())
                .build();

        return projectRepository.save(updatedProject);
    }

    @Override
    @Transactional
    public void deleteProjectById(Integer id) {
        if (projectRepository.findById(id).isEmpty()) {
            throw new ProjectNotFoundException(id);
        }

        if (!versionRepository.findByProject_id(id).isEmpty()) {
            throw new CascadeDeletionException("Esse Projeto não pode ser excluído pois já possui versão cadastrada.");
        }

        projectRepository.deleteById(id);
    }
}
