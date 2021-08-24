package br.com.devschool.demo.domain.model.internal.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestDTO {

    private String URL_Homolog;

    private Integer eventId;

    private Integer requestFatherId;

    private String URI_Prod;

    private String Description;

    private String Layer;

    private boolean status;

    private Integer order;
}