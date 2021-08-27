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

import br.com.devschool.demo.domain.model.internal.EventType;
import br.com.devschool.demo.domain.service.impl.EventTypeServiceImpl;

@ExtendWith(MockitoExtension.class)
class EventTypeControllerTest {

	@InjectMocks
	private EventTypeController controller;

	@Mock
	private EventTypeServiceImpl service;

	@Test
	void requestGetForEventTypesMustReturnStatusOk() {
		ResponseEntity<List<EventType>> results = controller.findAllEventType();
		Assertions.assertEquals(HttpStatus.OK, results.getStatusCode());
	}

	@Test
	void requestGetEventTypeByIdMustReturnStatusOk() {
		ResponseEntity<EventType> result = controller.findEventTypeId(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void requestCreateEventTypeMustReturnStatusOk() {
		ResponseEntity<EventType> result = controller.createEventType(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void requestUpdateEventTypeMustReturnStatusOk() {
		ResponseEntity<EventType> result = controller.updateEventType(any(), any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void requestdeleteEventTypeMustReturnStatusOk() {
		ResponseEntity<EventType> result = controller.deleteEventTypeId(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
}
