package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.model.internal.Project;
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
    public ResponseEntity<List<Project>> getAllProjects(@RequestParam(required = false, defaultValue = "") String name, @RequestParam(required = false) String status, @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(projectService.getAllProjects(name, status, pageable));
    }

    // show
    @GetMapping("/project/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Integer id){
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    // post
    @PostMapping("/project")
    public ResponseEntity<Project> createProject(@RequestBody Project project){
        return ResponseEntity.ok(projectService.createProject(project));
    }

    // put
    @PutMapping("/project/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer id, @RequestBody Project project) {
        return ResponseEntity.ok(projectService.updateProject(id, project));
    }

    // delete
    @DeleteMapping("/project/{id}")
    public ResponseEntity deleteProject(@PathVariable Integer id) {
        projectService.deleteProjectById(id);
        return ResponseEntity.ok().build();
    }
}
