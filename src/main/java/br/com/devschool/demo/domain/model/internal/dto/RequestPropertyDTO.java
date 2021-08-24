package br.com.devschool.demo.domain.model.internal.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestPropertyDTO {

    private Integer requestId;

    private String type;

    private String key;

    private Integer value;

    private String order;

}
