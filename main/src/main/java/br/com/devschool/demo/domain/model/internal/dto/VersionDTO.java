package br.com.devschool.demo.domain.model.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionDTO {
    private Integer id;

    private String versionNumber;

    private String gmud;

    private String description;

    private LocalDate deployDate;

    private Boolean status;

    private Integer order;

    private Integer versionCloneId;

    private Integer projectId;
}
