package br.com.devschool.demo.application.controller;

import static org.junit.jupiter.api.Assertions.*;
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

import br.com.devschool.demo.domain.model.internal.Request;
import br.com.devschool.demo.domain.service.impl.RequestServiceImpl;

@ExtendWith(MockitoExtension.class)
class RequestControllerTest {

	@InjectMocks
	private RequestController controller;
	
	@Mock
	private RequestServiceImpl service;

	@Test
	void requestGetForRequestsMustReturnStatusOk() {
		ResponseEntity<List<Request>> results = controller.findAllRequests();
		Assertions.assertEquals(HttpStatus.OK, results.getStatusCode());
	}
	
	@Test
	void requestGetRequestByIdMustReturnStatusOk() {
		ResponseEntity<Request> result = controller.findRequestById(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void requestCreateRequestMustReturnStatusOk() {
		ResponseEntity<Request> result = controller.createRequest(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void requestUpdateRequestMustReturnStatusOk() {
		ResponseEntity<Request> result = controller.updateRequest(any(), any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void requestdeleteRequestMustReturnStatusOk() {
		ResponseEntity<Request> result = controller.deleteRequest(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

}
