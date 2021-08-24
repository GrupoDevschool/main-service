package br.com.devschool.demo.domain.model.internal.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReturnMessage {
    private String message;
}
