package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.model.internal.Project;
import br.com.devschool.demo.domain.model.internal.dto.ProjectDTO;
import br.com.devschool.demo.domain.service.ProjectService;
import br.com.devschool.demo.infra.exception.ProjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/project")
    public ResponseEntity<List<ProjectDTO>> getAllProjects(@RequestParam(required = false, defaultValue = "") String name, @RequestParam(required = false) String status, @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(ProjectDTO.convertList(projectService.getAllProjects(name, status, pageable)));
    }

    // show
    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Integer id){
      ProjectDTO projectDTO  = new ProjectDTO(projectService.getProjectById(id));
        return ResponseEntity.ok(projectDTO);
    }

    // post
    @PostMapping("/project")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody Project project){
        ProjectDTO projectDTO = new ProjectDTO(projectService.createProject(project));
        return ResponseEntity.ok(projectDTO);
    }

    // put
    @PutMapping("/project/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Integer id, @RequestBody Project project) {
        ProjectDTO projectDTO = new ProjectDTO(projectService.updateProject(id,project));
        return ResponseEntity.ok(projectDTO);
    }

    // delete
    @DeleteMapping("/project/{id}")
    public ResponseEntity deleteProject(@PathVariable Integer id) {
        projectService.deleteProjectById(id);
        return ResponseEntity.ok().build();
    }
}
