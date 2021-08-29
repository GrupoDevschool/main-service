package br.com.devschool.demo.domain.model.internal.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.devschool.demo.domain.model.internal.RequestProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPropertyDTO {

    private Integer requestId;

    private String type;

    private String key;

    private Integer value;

    private String order;


    public RequestPropertyDTO(RequestProperty requestProperty) {
        this.requestId = requestProperty.getRequest().getId();
        this.type = requestProperty.getType();
        this.key = requestProperty.getKey();
        this.value = requestProperty.getValue();
        this.order = requestProperty.getOrder();
    }


    public static List<RequestPropertyDTO> covertList(List<RequestProperty> requestProperties) {
        return requestProperties.stream().map(RequestPropertyDTO::new).collect(Collectors.toList());
    }


}
