package br.com.devschool.demo.domain.service;

import br.com.devschool.demo.domain.model.internal.Request;
import br.com.devschool.demo.domain.model.internal.dto.RequestDTO;

import java.util.List;

public interface RequestService {
    List<Request> getAllRequests();

    Request getRequestById(Integer id);

    Request createRequest(RequestDTO requestDTO);

    Request updateRequest(Integer id, RequestDTO requestDTO);

    void deleteRequestById(Integer id);
}
