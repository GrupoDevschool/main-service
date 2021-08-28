package br.com.devschool.demo.domain.model.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPropertyDTO {

    private Integer requestId;

    private String type;

    private String key;

    private Integer value;

    private String order;


    public RequestPropertyDTO(RequestPropertyDTO requestPropertyDTO) {
        this.requestId = requestPropertyDTO.getRequestId();
        this.type = requestPropertyDTO.getKey();
        this.key = requestPropertyDTO.getKey();
        this.value = requestPropertyDTO.getValue();
        this.order = requestPropertyDTO.getOrder();
    }


    public static List<RequestPropertyDTO> covertList(List<RequestPropertyDTO> allProperties) {
        return allProperties.stream().map(RequestPropertyDTO::new).collect(Collectors.toList());
    }


}
