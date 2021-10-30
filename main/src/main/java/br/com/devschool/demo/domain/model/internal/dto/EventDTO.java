package br.com.devschool.demo.domain.model.internal.dto;

import br.com.devschool.demo.domain.model.internal.Event;
import br.com.devschool.demo.domain.model.internal.EventType;
import br.com.devschool.demo.domain.model.internal.Screen;
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
public class EventDTO {
    private Integer id;
    private boolean active;
    private Integer order;
    private String parameter;
    private Integer screenId;
    private Integer eventTypeId;


    public EventDTO(Event event) {
        this.id = event.getId();
        this.active = event.isActive();
        this.order = event.getOrder();
        this.parameter = event.getParameter();
        this.screenId = event.getScreen().getId();
        this.eventTypeId = event.getEventType().getId();
    }

    public static List<EventDTO> convertList(List<Event> events, Integer eventTypeId) {
        return events.stream().map(EventDTO::new).collect(Collectors.toList());
    }

}
