package br.com.devschool.demo.domain.service.impl;

import br.com.devschool.demo.domain.model.internal.Request;
import br.com.devschool.demo.domain.model.internal.RequestProperty;
import br.com.devschool.demo.domain.model.internal.dto.RequestPropertyDTO;
import br.com.devschool.demo.domain.service.RequestPropertyService;
import br.com.devschool.demo.infra.exception.RequestNotFoundException;
import br.com.devschool.demo.infra.exception.RequestPropertyNotFoundException;
import br.com.devschool.demo.infra.repository.RequestPropertyRepository;
import br.com.devschool.demo.infra.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RequestPropertyServiceImpl implements RequestPropertyService {
    private final RequestPropertyRepository requestPropertyRepository;
    private final RequestRepository requestRepository;

    @Override
    public List<RequestProperty> getAllRequestProperty() {
        return requestPropertyRepository.findAll();
    }

    public RequestProperty getRequestPropertyById(Integer id) {
        return requestPropertyRepository.findById(id).orElseThrow(() -> new RequestPropertyNotFoundException(id));
    }

    @Override
    public RequestProperty  createVRequestProperty(RequestPropertyDTO requestPropertyDTO) {
        Optional<Request> optionalRequest = requestRepository.findById(requestPropertyDTO.getRequestId());

        if (optionalRequest.isEmpty()) {
            throw new RequestNotFoundException(requestPropertyDTO.getRequestId());
        }

        Request existentRequest = optionalRequest.get();

        RequestProperty newRequestProperty = RequestProperty.builder()
                .request(existentRequest)
                .key(requestPropertyDTO.getKey())
                .value(requestPropertyDTO.getValue())
                .order(requestPropertyDTO.getOrder())
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        return requestPropertyRepository.save(newRequestProperty);
    }

    @Override
    public RequestProperty updateRequestProperty(Integer id, RequestPropertyDTO requestPropertyDTO) {
        Optional<RequestProperty> optionalRequestProperty = requestPropertyRepository.findById(id);

        if (optionalRequestProperty.isEmpty()) {
            throw new RequestPropertyNotFoundException(id);
        }

        RequestProperty existentRequestProperty = optionalRequestProperty.get();

        Optional<Request> optionalRequest = requestRepository.findById(requestPropertyDTO.getRequestId());

        if (optionalRequest.isEmpty()) {
            throw new RequestNotFoundException(requestPropertyDTO.getRequestId());
        }

        Request existentRequest = optionalRequest.get();

        RequestProperty newRequestProperty = RequestProperty.builder()
                .id(id)
                .request(existentRequest)
                .key(requestPropertyDTO.getKey())
                .value(requestPropertyDTO.getValue())
                .order(requestPropertyDTO.getOrder())
                .createdDate(existentRequestProperty.getCreatedDate())
                .updatedDate(LocalDate.now())
                .build();

        return requestPropertyRepository.save(newRequestProperty);
    }

    @Override
    public void deleteRequestPropertyById(Integer id) {
        if (requestPropertyRepository.findById(id).isEmpty()) {
            throw new RuntimeException();
        }

        requestPropertyRepository.deleteById(id);
    }

    @Override
    public List<RequestProperty> getByRequestId(Integer requestId) {
        return requestRepository.findAllByRequestId(requestId);
    }
}
