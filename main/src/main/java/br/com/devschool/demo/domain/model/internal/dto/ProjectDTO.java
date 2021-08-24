package br.com.devschool.demo.domain.model.internal.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDTO {
    private Integer id;
    private Boolean active;
    private Integer order;
    private String name;
}
