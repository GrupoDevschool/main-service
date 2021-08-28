package br.com.devschool.demo.application.controller;

import static org.junit.jupiter.api.Assertions.*;
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

import br.com.devschool.demo.domain.model.internal.Event;
import br.com.devschool.demo.domain.model.internal.dto.EventDTO;
import br.com.devschool.demo.domain.service.impl.EventServiceImpl;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

	@InjectMocks
	private EventController controller;
	
	@Mock
	private EventServiceImpl service;
	@Mock(answer = Answers.RETURNS_MOCKS)
	private Event event;
	
	@Test
	void requestGetForEventsMustReturnStatusOk() {
		ResponseEntity<List<EventDTO>> results = controller.getAllEvents();
		Assertions.assertEquals(HttpStatus.OK, results.getStatusCode());
	}
	
	@Test
	void requestGetEventByIdMustReturnStatusOk() {
		doReturn(event).when(service).getEventById(any());
		ResponseEntity<EventDTO> result = controller.getEventById(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void requestCreateEventMustReturnStatusOk() {
		ResponseEntity<Event> result = controller.createEvent(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void requestUpdateEventMustReturnStatusOk() {
		ResponseEntity<Event> result = controller.updateEvent(any(), any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void requestdeleteEventMustReturnStatusOk() {
		ResponseEntity<Event> result = controller.deleteEvent(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
}
