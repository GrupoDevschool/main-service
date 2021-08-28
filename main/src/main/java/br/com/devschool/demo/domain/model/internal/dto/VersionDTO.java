package br.com.devschool.demo.domain.model.internal.dto;

import br.com.devschool.demo.domain.model.internal.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
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


    public VersionDTO(Version version) {
        this.id = version.getId();
        this.versionNumber = version.getVersionNumber();
        this.gmud = version.getGmud();
        this.description = version.getDescription();
        this.deployDate= version.getDeployDate();
        this.status = version.getStatus();
        this.order = version.getOrder();
        this.versionCloneId = version.getVersionCloneId();
        this.projectId = version.getProject().getId();
    }

    public static List<VersionDTO> convertList(List<Version> versions) {
        return versions.stream().map(VersionDTO::new).collect(Collectors.toList());
    }
}
