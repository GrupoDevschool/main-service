package br.com.devschool.demo.domain.service;

import br.com.devschool.demo.domain.model.internal.Project;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects(String name, String status, Pageable pageable);

    Project getProjectById(Integer id);

    Project createProject(Project project);

    Project updateProject(Integer id, Project project);

    void deleteProjectById(Integer id);
}
