package br.com.devschool.demo.domain.model.internal.responseDto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.devschool.demo.domain.model.internal.Screen;
import lombok.Getter;

@Getter
public class ScreenResponseDTO {

	private Integer id;
	private Boolean active;
	private Integer fatherScreenId;
	private String name;
	private String image;
	private Integer order;
	private Integer versionId;
	private Integer versionClonedId;
	
	public ScreenResponseDTO(Screen screen) {
		Integer fatherId = null;
		if (screen.getScreenFather() != null) {
			fatherId = screen.getScreenFather().getId(); 
		}
		
		Integer cloneVersionId = null;
		if (screen.getCloneVersion() != null) {
			cloneVersionId = screen.getCloneVersion().getId();
		}
		
		this.id = screen.getId();
		this.active = screen.isActive();
		this.fatherScreenId = fatherId;
		this.name = screen.getName();
		this.image = screen.getImage();
		this.order = screen.getOrder();
		this.versionId = screen.getVersion().getId();
		this.versionClonedId = cloneVersionId;
	}
	
	public static List<ScreenResponseDTO> convertList(List<Screen> screen) {
        return screen.stream().map(ScreenResponseDTO::new).collect(Collectors.toList());
    }
	
}
