package br.com.devschool.demo.domain.model.internal.dto;

import br.com.devschool.demo.domain.model.internal.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private Integer id;
    private Boolean active;
    private String name;

    public ProjectDTO(Project project){
        this.id = project.getId();
        this.active = project.isStatus();
        this.name = project.getName();
    }

    public static List<ProjectDTO> convertList(List<Project> projects){
        return projects.stream().map(ProjectDTO::new).collect(Collectors.toList());
    }
}

