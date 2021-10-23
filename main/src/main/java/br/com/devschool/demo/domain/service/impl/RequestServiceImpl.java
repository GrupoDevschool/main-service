package br.com.devschool.demo.domain.service.impl;

import br.com.devschool.demo.domain.model.internal.Event;
import br.com.devschool.demo.domain.model.internal.Request;
import br.com.devschool.demo.domain.model.internal.dto.RequestDTO;
import br.com.devschool.demo.domain.service.RequestService;
import br.com.devschool.demo.infra.exception.EventNotFoundException;
import br.com.devschool.demo.infra.exception.RequestNotFoundException;
import br.com.devschool.demo.infra.exception.ScreenNotFoundException;
import br.com.devschool.demo.infra.repository.EventRepository;
import br.com.devschool.demo.infra.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public Request getRequestById(Integer id) {
        return requestRepository.findById(id).orElseThrow(() -> new RequestNotFoundException(id));
    }

    @Override
    public Request createRequest(RequestDTO requestDTO) {
        Optional<Event> optionalEvent = eventRepository.findById(requestDTO.getEventId());

        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException(requestDTO.getEventId());
        }

        Event existentEvent = optionalEvent.get();

        if (Objects.isNull(requestDTO.getRequestFatherId())) {

            Request newRequest = Request.builder()
                    .uri_homolog(requestDTO.getUri_homolog())
                    .event(existentEvent)
                    .uri_prod(requestDTO.getUri_prod())
                    .description(requestDTO.getDescription())
                    .layer(requestDTO.getLayer())
                    .status(requestDTO.isStatus())
                    .order(requestDTO.getOrder())
                    .createdDate(LocalDate.now())
                    .updatedDate(LocalDate.now())
                    .build();

            return requestRepository.save(newRequest);
        }

        Optional<Request> optionalFatherRequest = requestRepository.findById(requestDTO.getRequestFatherId());

        if (optionalFatherRequest.isEmpty()) {
            throw new ScreenNotFoundException(requestDTO.getRequestFatherId());
        }

        Request newRequest = Request.builder()
                .uri_homolog(requestDTO.getUri_homolog())
                .event(existentEvent)
                .requestFather(optionalFatherRequest.get())
                .uri_prod(requestDTO.getUri_prod())
                .description(requestDTO.getDescription())
                .layer(requestDTO.getLayer())
                .status(requestDTO.isStatus())
                .order(requestDTO.getOrder())
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        return requestRepository.save(newRequest);
    }

    @Override
    public Request updateRequest(Integer id, RequestDTO requestDTO) {
        Optional<Request> optionalRequest = requestRepository.findById(id);

        if (optionalRequest.isEmpty()) {
            throw new RuntimeException();
        }

        Request existentRequest = optionalRequest.get();

        Optional<Event> optionalEvent = eventRepository.findById(requestDTO.getEventId());

        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException(requestDTO.getEventId());
        }

        Event existentEvent = optionalEvent.get();

        if (Objects.isNull(requestDTO.getRequestFatherId())) {

            Request updatedRequest = Request.builder()
                    .id(id)
                    .uri_homolog(requestDTO.getUri_homolog())
                    .event(existentEvent)
                    .uri_prod(requestDTO.getUri_prod())
                    .description(requestDTO.getDescription())
                    .layer(requestDTO.getLayer())
                    .status(requestDTO.isStatus())
                    .order(requestDTO.getOrder())
                    .createdDate(LocalDate.now())
                    .updatedDate(LocalDate.now())
                    .build();

            return requestRepository.save(updatedRequest);
        }

        Optional<Request> optionalFatherRequest = requestRepository.findById(requestDTO.getRequestFatherId());

        if (optionalFatherRequest.isEmpty()) {
            throw new ScreenNotFoundException(requestDTO.getRequestFatherId());
        }

        Request updatedRequest = Request.builder()
                .id(id)
                .uri_homolog(requestDTO.getUri_homolog())
                .event(existentEvent)
                .requestFather(optionalFatherRequest.get())
                .uri_prod(requestDTO.getUri_prod())
                .description(requestDTO.getDescription())
                .layer(requestDTO.getLayer())
                .status(requestDTO.isStatus())
                .order(requestDTO.getOrder())
                .createdDate(existentRequest.getCreatedDate())
                .updatedDate(LocalDate.now())
                .build();

        return requestRepository.save(updatedRequest);
    }

    @Override
    public void deleteRequestById(Integer id) {
        if (requestRepository.findById(id).isEmpty()) {
            throw new RuntimeException();
        }

        requestRepository.deleteById(id);
    }

    @Override
    public List<Request> getEventById(Integer eventId) {
        return requestRepository.findAllByEventId(eventId);
    }
}
