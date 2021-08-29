package br.com.devschool.demo.domain.model.internal.dto;
import br.com.devschool.demo.domain.model.internal.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventTypeDTO {
    private Integer id;
    private String name;
    private boolean status;
    private Integer order;


    public EventTypeDTO(EventType eventType) {
        this.id = eventType.getId();
        this.name = eventType.getName();
        this.status = eventType.isStatus();
        this.order = eventType.getOrder();
    }


//    public static List<EventTypeDTO> convertList(List<EventTypeDTO> eventsTypes){
//        return eventsTypes.stream().map(EventTypeDTO::new).collect(Collectors.toList());
//    }

}
