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

	private Integer id;
	
    private String uri_homolog;

    private Integer eventId;

    private Integer requestFatherId;

    private String uri_prod;

    private String description;

    private String layer;

    private boolean status;

    private Integer order;

    public RequestDTO(Request request) {
    	this.id = request.getId();
        this.uri_homolog = request.getUri_homolog();
        this.eventId = request.getEvent().getId();
        if (request.getRequestFather() != null) {
            this.requestFatherId = request.getRequestFather().getId();
        }
        this.uri_prod = request.getUri_prod();
        this.description = request.getDescription();

        this.layer = request.getLayer();
        this.status = request.isStatus();
        this.order = request.getOrder();
    }

    public static List<RequestDTO> convertList(List<Request> requests) {
        return requests.stream().map(RequestDTO::new).collect(Collectors.toList());
    }
}
