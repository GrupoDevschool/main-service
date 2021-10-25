package br.com.devschool.demo.application.controller;

import static br.com.devschool.demo.util.JsonConvertionUtils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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

import br.com.devschool.demo.domain.model.internal.Event;
import br.com.devschool.demo.domain.model.internal.Request;
import br.com.devschool.demo.domain.model.internal.dto.RequestDTO;
import br.com.devschool.demo.domain.service.RequestService;
import br.com.devschool.demo.infra.exception.CascadeDeletionException;
import br.com.devschool.demo.infra.exception.RequestNotFoundException;
import br.com.devschool.demo.infra.exception.ScreenNotFoundException;
import br.com.devschool.demo.infra.exception.VersionNotFoundException;

@ExtendWith(MockitoExtension.class)
class RequestControllerTest {

	private static final String REQUEST_API_URL_PATH = "/request";
	private static final Integer VALID_ID = 1;
	private static final Integer INVALID_ID = 2;
	private static final Integer VALID_EVENT_ID = 1;

	private Request requestBuilder() {
		return Request.builder()
				.id(VALID_ID)
				.uri_homolog("uri_homolog")
				.event(Event.builder().id(1).build())
				.requestFather(null)
				.uri_prod("uri_prod")
				.description("description")
				.layer("layer")
				.status(true)
				.order(1)
				.createdDate(LocalDate.now())
				.updatedDate(LocalDate.now())
				.build();
	}

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private RequestController controller;

	@Mock
	private RequestService requestService;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.setControllerAdvice(new ErrorHandler())
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.build();
	}

	@Test
	void requisicaoGetDeTodasAsRequisicoesDeUmEventoComIdDeEventoValidoDeveRetornarStatusCode200() throws Exception {

		mockMvc.perform(get(REQUEST_API_URL_PATH)
				.param("eventId", VALID_EVENT_ID.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

//	@Test
//	void requisicaoGetDeTodasAsRequisicoesDeUmEventoComIdDeEventoInvalidoDeveRetornarStatusCode204() throws Exception {
//		when(requestService.getAllRequests(anyInt(), any(Pageable.class)))
//			.thenThrow(ScreensNotListedException.class);
//
//		mockMvc.perform(get(REQUEST_API_URL_PATH)
//				.param("eventId", INVALID_EVENT_ID.toString())
//				.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isNoContent());
//	}

	@Test
	void requisicaoGetComParametroIdEventValidoDeveRetornarStatusCode200() throws Exception {
		when(requestService.getRequestById(VALID_ID)).thenReturn(requestBuilder());

		mockMvc.perform(get(REQUEST_API_URL_PATH + "/" + VALID_ID)).andExpect(status().isOk());
	}

//	@Test
//	void requisicaoGetComParametroIdRequestInvalidoDeveRetornarStatusCode204() throws Exception {
//		when(requestService.getRequestById(INVALID_ID))
//			.thenThrow(RequestNotFoundException.class);
//
//		mockMvc.perform(get(REQUEST_API_URL_PATH + "/" + INVALID_ID))
//		.andExpect(status().isNoContent());
//	}

	@Test
	void chamadaPostComRequisicaoValidaNoBodyDeveRetornarStatusCode200() throws Exception {
		when(requestService.createRequest(any(RequestDTO.class))).thenReturn(requestBuilder());

		mockMvc.perform(post(REQUEST_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(requestBuilder())))
		.andExpect(status().isOk());
	}

	@Test
	void chamadaPostCriandoUmaRequisicaoParaUmEventoQueNaoExisteDeveRetornarStatusCode500() throws Exception {
		when(requestService.createRequest(any(RequestDTO.class))).thenThrow(VersionNotFoundException.class);

		mockMvc.perform(post(REQUEST_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(requestBuilder())))
		.andExpect(status().isInternalServerError());
	}

	@Test
	void chamadaPostCriandoUmaRequisicaoComUmaRequisicaoPaiQueNaoExisteDeveRetornarStatusCode500() throws Exception {
		when(requestService.createRequest(any(RequestDTO.class))).thenThrow(RequestNotFoundException.class);

		mockMvc.perform(post(REQUEST_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(requestBuilder())))
		.andExpect(status().isInternalServerError());
	}

	@Test
	void quandoPUTForChamadoEUmaRequisicaoForAtualizadaRetorneStatusIsOk() throws Exception {
		Request request = this.requestBuilder();

		when(requestService.updateRequest(anyInt(), any(RequestDTO.class))).thenReturn(request);

		mockMvc.perform(put(REQUEST_API_URL_PATH + "/" + VALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new RequestDTO())))
		.andExpect(status().isOk());
	}

	@Test
	void quandoPutForChamadoPraAtualizarUmaRequisicaoQueNaoExisteDeveRetornarStatusCode500() throws Exception {
		when(requestService.updateRequest(anyInt(), any(RequestDTO.class))).thenThrow(ScreenNotFoundException.class);

		mockMvc.perform(put(REQUEST_API_URL_PATH + "/" + INVALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new RequestDTO())))
		.andExpect(status().isInternalServerError());
	}

	void quandoPutForChamadoPraAtualizarUmaRequisicaoParaUmEventoQueNaoExisteDeveRetornarStatusCode500() throws Exception {
		when(requestService.updateRequest(anyInt(), any(RequestDTO.class))).thenThrow(VersionNotFoundException.class);

		mockMvc.perform(put(REQUEST_API_URL_PATH + "/" + VALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new RequestDTO())))
		.andExpect(status().isInternalServerError());
	}

	@Test
	void quandoDELETEForChamadoComUmIdValidoRetorneIsOk() throws Exception {
		doNothing().when(requestService).deleteRequestById(VALID_ID);

		mockMvc.perform(delete(REQUEST_API_URL_PATH + "/" + VALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	void quandoDELETEForChamadoComUmIDInvalidoRetorneStatusCode500() throws Exception {
		doThrow(RequestNotFoundException.class).when(requestService).deleteRequestById(INVALID_ID);

		mockMvc.perform(delete(REQUEST_API_URL_PATH + "/" + INVALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isInternalServerError());
	}

	@Test
	void quandoDELETEForChamadoMasRequestPossuirPropriedadesJaCadastradasRetorneBadRequest() throws Exception {

		doThrow(CascadeDeletionException.class).when(requestService).deleteRequestById(VALID_ID);

		mockMvc.perform(delete(REQUEST_API_URL_PATH + "/" + VALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}

}
