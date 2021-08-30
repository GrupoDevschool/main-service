package br.com.devschool.demo.domain.model.internal.dto;

import br.com.devschool.demo.domain.model.internal.Screen;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScreenDTO {
    private Integer id;
    private String name;
    private String image;
    private boolean active;
    private Integer order;
    private String urlog;
    private Integer versionId;
    private Integer screenFatherId;
    private Integer cloneVersionId;

    public ScreenDTO(Screen screen) {
        this.id = screen.getId();
        this.name = screen.getName();
        this.image = screen.getImage();
        this.active = screen.isActive();
        this.order = screen.getOrder();
        this.versionId = screen.getVersion().getId();
        this.screenFatherId = screen.getScreenFather().getId();
        this.cloneVersionId = screen.getCloneVersion().getId();

    }

}
