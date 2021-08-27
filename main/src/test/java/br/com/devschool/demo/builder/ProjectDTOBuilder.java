package br.com.devschool.demo.builder;

import lombok.Builder;

@Builder
public class ProjectDTOBuilder {

    @Builder.Default
    private Integer id = 1;

    @Builder.Default
    private Boolean active = true;

    @Builder.Default
    private String name = "Projeto X";

}
