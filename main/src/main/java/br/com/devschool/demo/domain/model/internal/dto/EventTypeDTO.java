package br.com.devschool.demo.domain.model.internal.dto;
import java.util.List;
import java.util.stream.Collectors;

import br.com.devschool.demo.domain.model.internal.EventType;
import lombok.*;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventTypeDTO {
    private Integer id;
    private String name;
    private boolean status;

    public EventTypeDTO(EventType eventType) {
        this.id = eventType.getId();
        this.name = eventType.getName();
        this.status = eventType.isStatus();
    }

    public static List<EventTypeDTO> convertList(List<EventType> eventsTypes){
        return eventsTypes.stream().map(EventTypeDTO::new).collect(Collectors.toList());
    }

}
