package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.model.internal.Event;
import br.com.devschool.demo.domain.model.internal.EventType;
import br.com.devschool.demo.domain.model.internal.Screen;
import br.com.devschool.demo.domain.model.internal.dto.EventDTO;
import br.com.devschool.demo.domain.service.impl.EventServiceImpl;
import br.com.devschool.demo.infra.exception.CascadeDeletionException;
import br.com.devschool.demo.infra.exception.EventNotFoundException;
import br.com.devschool.demo.util.JsonConvertionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static br.com.devschool.demo.util.JsonConvertionUtils.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

	private static final String EVENT_API_URL_PATH = "/event";
	private static final Integer EVENT_VALID_ID = 1;
	private static final Integer EVENT_INVALID_ID = 2;
	private Event eventBuilder() {
		return Event.builder()
				.id(2)
				.screen(Screen.builder().id(1).build())
				.eventType(EventType.builder().id(1).build())
				.active(true)
				.order(1)
				.parameter("parametro x")
				.build();
	}

	@InjectMocks
	private EventController controller;
	
	@Mock
	private EventServiceImpl service;

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
	void requestGetForEventsMustReturnStatusOk() throws Exception {
		Event event = eventBuilder();

		when(service.getAllEvents()).thenReturn(Collections.singletonList(event));

		mockMvc.perform(get(EVENT_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void quandoGETForChamadoPassandoUmIDValidoRetorneStatusIsOk() throws Exception {
		Event event = eventBuilder();

		when(service.getEventById(EVENT_VALID_ID)).thenReturn(event);

		mockMvc.perform(get(EVENT_API_URL_PATH + "/" + EVENT_VALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void quandoGETForChamadoPassandoUmIDInvalidoRetorneStatusInternalServerError() throws Exception {
		when(service.getEventById(EVENT_INVALID_ID)).thenThrow(EventNotFoundException.class);

		mockMvc.perform(get(EVENT_API_URL_PATH + "/" + EVENT_INVALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	void quandoPOSTForChamadoEUmEventoForCriadoRetorneStatusIsOk() throws Exception {
		Event event = eventBuilder();
		EventDTO eventDTO = new EventDTO(event);

		when(service.createEvent(eventDTO)).thenReturn(event);

		mockMvc.perform(post(EVENT_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(eventDTO)))
				.andExpect(status().isOk());
	}
	
	@Test
	void quandoPUTForChamadoEUmEventoForAtualizadoRetorneStatusIsOk() throws Exception {
		Event event = eventBuilder();
		EventDTO eventDTO = new EventDTO(event);

		when(service.updateEvent(EVENT_VALID_ID, eventDTO)).thenReturn(event);

		mockMvc.perform(put(EVENT_API_URL_PATH + "/" + EVENT_VALID_ID )
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(eventDTO)))
				.andExpect(status().isOk());
	}
	
	@Test
	void quandoDELETEForChamadoComUmIDValidoRetorneStatusIsOk() throws Exception {
		doNothing().when(service).deleteEventById(EVENT_VALID_ID);

		mockMvc.perform(delete(EVENT_API_URL_PATH + "/" + EVENT_VALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void quandoDELETEForChamadoMasEventoPossuirRequisicoesJaCadastradasRetorneStatusBadRequest() throws Exception {
		doThrow(CascadeDeletionException.class).when(service).deleteEventById(EVENT_VALID_ID);

		mockMvc.perform(delete(EVENT_API_URL_PATH + "/" + EVENT_VALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
}
