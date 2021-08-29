package br.com.devschool.demo.application.controller;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.devschool.demo.domain.model.internal.RequestProperty;
import br.com.devschool.demo.domain.model.internal.dto.RequestPropertyDTO;
import br.com.devschool.demo.domain.service.impl.RequestPropertyServiceImpl;

@ExtendWith(MockitoExtension.class)
class RequestPropertyControllerTest {

	@InjectMocks
	private RequestPropertyController controller;

	@Mock
	private RequestPropertyServiceImpl service;

	@Test
	void requestGetForRequestPropertysMustReturnStatusOk() {
		ResponseEntity<List<RequestPropertyDTO>> results = controller.getAllRequestProperty();
		Assertions.assertEquals(HttpStatus.OK, results.getStatusCode());
	}

	@Test
	void requestGetRequestPropertyByIdMustReturnStatusOk() {
		ResponseEntity<RequestProperty> result = controller.getRequestPropertyById(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void requestCreateRequestPropertyMustReturnStatusOk() {
		ResponseEntity<RequestProperty> result = controller.createRequestProperty(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void requestUpdateRequestPropertyMustReturnStatusOk() {
		ResponseEntity<RequestProperty> result = controller.updateRequestProperty(any(), any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void requestdeleteRequestPropertyMustReturnStatusOk() {
		ResponseEntity<RequestProperty> result = controller.deleteRequestProperty(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

}
