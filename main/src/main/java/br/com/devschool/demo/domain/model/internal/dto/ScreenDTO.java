package br.com.devschool.demo.domain.model.internal.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
}
