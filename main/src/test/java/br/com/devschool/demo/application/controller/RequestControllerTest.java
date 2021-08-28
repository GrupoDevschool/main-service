package br.com.devschool.demo.application.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.devschool.demo.domain.model.internal.Request;
import br.com.devschool.demo.domain.model.internal.dto.RequestDTO;
import br.com.devschool.demo.domain.service.impl.RequestServiceImpl;

@ExtendWith(MockitoExtension.class)
class RequestControllerTest {

	@InjectMocks
	private RequestController controller;
	
	@Mock
	private RequestServiceImpl service;
	@Mock
	private List<RequestDTO> requestsDTO;
	@Mock(answer = Answers.RETURNS_MOCKS)
	private Request request;
	
	@Test
	void requestGetForRequestsMustReturnStatusOk() {
		doReturn(requestsDTO).when(service).getAllRequests();
		ResponseEntity<List<RequestDTO>> results = controller.findAllRequests();
		Assertions.assertEquals(HttpStatus.OK, results.getStatusCode());
	}
	
	@Test
	void requestGetRequestByIdMustReturnStatusOk() {
		doReturn(request).when(service).getRequestById(any());
		ResponseEntity<RequestDTO> result = controller.findRequestById(any());
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
