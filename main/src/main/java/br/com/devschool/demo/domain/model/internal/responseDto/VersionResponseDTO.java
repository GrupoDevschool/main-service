package br.com.devschool.demo.domain.model.internal.responseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.com.devschool.demo.domain.model.internal.Version;
import lombok.Getter;

@Getter
public class VersionResponseDTO {

	private Integer id;
	private Boolean active;
	private String number;
	private LocalDate date;
	private String gmud;
	private Integer order;
	private Integer screens;
	
	public VersionResponseDTO(Version version) {
		Integer screens = null;
		if (version.getScreens() != null) {
			screens = version.getScreens().size();
		}
        this.id = version.getId();
        this.gmud = version.getGmud();
//        this.description = version.getDescription();
        this.date = version.getDeployDate();
        this.active = version.getStatus();
        this.order = version.getOrder();
        this.number = version.getVersionNumber();
//        this.versionCloneId = version.getVersionCloneId();
//        this.projectId = version.getProject().getId();
        this.screens = screens;
    }
	
	public static List<VersionResponseDTO> convertList(List<Version> versions) {
        return versions.stream().map(VersionResponseDTO::new).collect(Collectors.toList());
    }
}
