package br.com.devschool.demo.domain.service;

import br.com.devschool.demo.domain.model.internal.RequestProperty;
import br.com.devschool.demo.domain.model.internal.dto.RequestPropertyDTO;

import java.util.List;

public interface RequestPropertyService {
    List<RequestProperty> getAllRequestProperty();

    RequestProperty getRequestPropertyById(Integer id);

    RequestProperty createVRequestProperty(RequestPropertyDTO requestPropertyDTO);

    RequestProperty updateRequestProperty(Integer id, RequestPropertyDTO requestPropertyDTO);

    void deleteRequestPropertyById(Integer id);
}
