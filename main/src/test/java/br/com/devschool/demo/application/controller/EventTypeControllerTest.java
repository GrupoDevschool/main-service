package br.com.devschool.demo.application.controller;

import static br.com.devschool.demo.util.JsonConvertionUtils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import br.com.devschool.demo.domain.model.internal.Event;
import br.com.devschool.demo.domain.model.internal.dto.EventTypeDTO;
import br.com.devschool.demo.infra.exception.CascadeDeletionException;
import br.com.devschool.demo.infra.exception.EventTypeNotFoundException;
import br.com.devschool.demo.util.JsonConvertionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.devschool.demo.domain.model.internal.EventType;
import br.com.devschool.demo.domain.service.impl.EventTypeServiceImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class EventTypeControllerTest {

	private static final String EVENTTYPE_API_URL_PATH = "/eventType";
	private static final Integer EVENTTYPE_VALID_ID = 1;
	private static final Integer EVENTTYPE_INVALID_ID = 2;
	private EventType eventTypeBuilder() {
		return EventType.builder()
				.id(2)
				.name("tipo 1")
				.status(true)
				.build();
	}

	@InjectMocks
	private EventTypeController controller;

	@Mock
	private EventTypeServiceImpl service;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new ErrorHandler())
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.build();
	}

	@Test
	void requestGetForEventTypesMustReturnStatusOk() throws Exception {
		EventType eventType = eventTypeBuilder();

		when(service.findAllEventType()).thenReturn(Collections.singletonList(eventType));

		mockMvc.perform(get(EVENTTYPE_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void quandoGETForChamadoPassandoUmIDValidoRetorneStatusIsOk() throws Exception {
		EventType eventType = eventTypeBuilder();

		when(service.findEventTypeId(EVENTTYPE_VALID_ID)).thenReturn(eventType);

		mockMvc.perform(get(EVENTTYPE_API_URL_PATH + "/" + EVENTTYPE_VALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void quandoGETForChamadoPassandoUmIDInvalidoRetorneStatusInternalServerError() throws Exception {

		when(service.findEventTypeId(EVENTTYPE_INVALID_ID)).thenThrow(EventTypeNotFoundException.class);

		mockMvc.perform(get(EVENTTYPE_API_URL_PATH + "/" + EVENTTYPE_INVALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}

	@Test
	void quandoPOSTForChamadoEUmTipoDeEventoForCriadoRetorneStatusIsOk() throws Exception {
		EventType eventType = eventTypeBuilder();

		when(service.createEventType(eventType)).thenReturn(eventType);

		mockMvc.perform(post(EVENTTYPE_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(eventType)))
				.andExpect(status().isOk());
	}

	@Test
	void quandoPUTForChamadoEUmTipodeEventoForAtualizadoRetorneStatusIsOk() throws Exception {
		EventType eventType = eventTypeBuilder();

		when(service.updateEventType(EVENTTYPE_VALID_ID, eventType)).thenReturn(eventType);

		mockMvc.perform(put(EVENTTYPE_API_URL_PATH + "/" + EVENTTYPE_VALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(eventType)))
				.andExpect(status().isOk());
	}

	@Test
	void quandoDELETEForChamadoComUmIDValidoRetorneStatusIsOk() throws Exception {
		doNothing().when(service).deleteEventTypeId(EVENTTYPE_VALID_ID);

		mockMvc.perform(delete(EVENTTYPE_API_URL_PATH + "/" + EVENTTYPE_VALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void quandoDELETEForChamadoMasTipoDeEventoPossuirEventosJaCadastradosRetorneStatusBadRequest() throws Exception {
		doThrow(CascadeDeletionException.class).when(service).deleteEventTypeId(EVENTTYPE_INVALID_ID);

		mockMvc.perform(delete(EVENTTYPE_API_URL_PATH + "/" + EVENTTYPE_INVALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}


}
