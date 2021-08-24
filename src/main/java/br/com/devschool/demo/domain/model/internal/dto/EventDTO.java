package br.com.devschool.demo.domain.model.internal.dto;

import br.com.devschool.demo.domain.model.internal.EventType;
import br.com.devschool.demo.domain.model.internal.Screen;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EventDTO {
    private boolean active;
    private Integer order;
    private String parameter;
    private Integer screenId;
    private Integer eventTypeId;
}
