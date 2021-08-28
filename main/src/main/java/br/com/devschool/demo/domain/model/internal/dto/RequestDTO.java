package br.com.devschool.demo.domain.model.internal.dto;

import br.com.devschool.demo.domain.model.internal.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {

    private String URL_Homolog;

    private Integer eventId;

    private Integer requestFatherId;

    private String URI_Prod;

    private String Description;

    private String Layer;

    private boolean status;

    private Integer order;

    public RequestDTO(Request request) {
        this.URL_Homolog = request.getURL_Homolog();
        this.eventId = request.getEvent().getId();
        if (request.getRequestFather() != null) {
            this.requestFatherId = request.getRequestFather().getId();
        }
        this.URI_Prod = request.getURI_Prod();
        this.Description = request.getDescription();

        this.Layer = request.getLayer();
        this.status = request.isStatus();
        this.order = request.getOrder();
    }

    public static List<RequestDTO> convertList(List<Request> requests) {
        return requests.stream().map(RequestDTO::new).collect(Collectors.toList());
    }
}
