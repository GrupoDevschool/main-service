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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.devschool.demo.domain.model.internal.Screen;
import br.com.devschool.demo.domain.model.internal.Version;
import br.com.devschool.demo.domain.model.internal.dto.ScreenDTO;
import br.com.devschool.demo.domain.service.ScreenService;
import br.com.devschool.demo.infra.exception.CascadeDeletionException;
import br.com.devschool.demo.infra.exception.ScreenNotFoundException;
import br.com.devschool.demo.infra.exception.ScreensNotListedException;
import br.com.devschool.demo.infra.exception.VersionNotFoundException;

@ExtendWith(MockitoExtension.class)
class ScreenControllerTest {

	private static final String SCREEN_API_URL_PATH = "/screen";
	private static final Integer VALID_ID = 1;
	private static final Integer INVALID_ID = 2;
	private static final Integer VALID_VERSION_ID = 1;
	private static final Integer INVALID_VERSION_ID = 2;

	private Screen screenBuilder() {
		return Screen.builder()
				.id(VALID_ID)
				.version(Version.builder().id(VALID_VERSION_ID).build())
				.screenFather(null)
				.cloneVersion(null)
				.name("Example")
				.image("image.jpg")
				.active(true)
				.order(1)
				.urlog("url")
				.createdDate(LocalDate.now())
				.updatedDate(LocalDate.now())
				.build();
	}

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private ScreenController controller;

	@Mock
	private ScreenService screenService;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.setControllerAdvice(new ErrorHandler())
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.build();
	}

	@Test
	void requisicaoGetDeTodasAsTelasDeUmaVersaoComIdDeVersaoValidoDeveRetornarStatusCode200() throws Exception {

		mockMvc.perform(get(SCREEN_API_URL_PATH)
				.param("versionId", VALID_VERSION_ID.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	void requisicaoGetDeTodasAsTelasDeUmaVersaoComIdDeVersaoInvalidoDeveRetornarStatusCode204() throws Exception {
		when(screenService.getAllScreens())
			.thenThrow(ScreensNotListedException.class);

		mockMvc.perform(get(SCREEN_API_URL_PATH)
				.param("versionId", INVALID_VERSION_ID.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}

	@Test
	void requisicaoGetComParametroIdScreenValidoDeveRetornarStatusCode200() throws Exception {
		when(screenService.getScreenById(VALID_ID)).thenReturn(screenBuilder());

		mockMvc.perform(get(SCREEN_API_URL_PATH + "/" + VALID_ID)).andExpect(status().isOk());
	}

//	@Test
//	void requisicaoGetComParametroIdScreenInvalidoDeveRetornarStatusCode204() throws Exception {
//		when(screenService.getScreenById(INVALID_ID))
//			.thenThrow(ScreenNotFoundException.class);
//		
//		mockMvc.perform(get(SCREEN_API_URL_PATH + "/" + INVALID_ID))
//		.andExpect(status().isNoContent());
//	}

	@Test
	void requisicaoPostComTelaValidaNoBodyDeveRetornarStatusCode200() throws Exception {
		when(screenService.createScreen(any(ScreenDTO.class))).thenReturn(screenBuilder());

		mockMvc.perform(post(SCREEN_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(screenBuilder())))
		.andExpect(status().isOk());
	}

	@Test
	void requisicaoPostCriandoUmaTelaParaUmaVersaoQueNaoExisteDeveRetornarStatusCode500() throws Exception {
		when(screenService.createScreen(any(ScreenDTO.class))).thenThrow(VersionNotFoundException.class);

		mockMvc.perform(post(SCREEN_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(screenBuilder())))
		.andExpect(status().isInternalServerError());
	}

	@Test
	void requisicaoPostCriandoUmaTelaComUmaTelaPaiQueNaoExisteDeveRetornarStatusCode500() throws Exception {
		when(screenService.createScreen(any(ScreenDTO.class))).thenThrow(ScreenNotFoundException.class);

		mockMvc.perform(post(SCREEN_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(screenBuilder())))
		.andExpect(status().isInternalServerError());
	}

	@Test
	void quandoPUTForChamadoEUmaTelaForAtualizadaRetorneStatusIsOk() throws Exception {
		Screen screen = this.screenBuilder();

		when(screenService.updateScreen(anyInt(), any(ScreenDTO.class))).thenReturn(screen);

		mockMvc.perform(put(SCREEN_API_URL_PATH + "/" + VALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new ScreenDTO())))
		.andExpect(status().isOk());
	}

	@Test
	void quandoPutForChamadoPraAtualizarUmaTelaQueNaoExisteDeveRetornarStatusCode500() throws Exception {
		when(screenService.updateScreen(anyInt(), any(ScreenDTO.class))).thenThrow(ScreenNotFoundException.class);

		mockMvc.perform(put(SCREEN_API_URL_PATH + "/" + INVALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new ScreenDTO())))
		.andExpect(status().isInternalServerError());
	}

	void quandoPutForChamadoPraAtualizarUmaTelaParaUmaVersaoQueNaoExisteDeveRetornarStatusCode500() throws Exception {
		when(screenService.updateScreen(anyInt(), any(ScreenDTO.class))).thenThrow(VersionNotFoundException.class);

		mockMvc.perform(put(SCREEN_API_URL_PATH + "/" + VALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new ScreenDTO())))
		.andExpect(status().isInternalServerError());
	}

	@Test
	void quandoDELETEForChamadoComUmIDValidoRetorneIsOk() throws Exception {
		doNothing().when(screenService).deleteScreenById(VALID_ID);

		mockMvc.perform(delete(SCREEN_API_URL_PATH + "/" + VALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	void quandoDELETEForChamadoComUmIDInvalidoRetorneStatusCode500() throws Exception {
		doThrow(ScreenNotFoundException.class).when(screenService).deleteScreenById(INVALID_ID);

		mockMvc.perform(delete(SCREEN_API_URL_PATH + "/" + INVALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isInternalServerError());
	}

	@Test
	void quandoDELETEForChamadoMasScreenPossuirEventosJaCadastradasRetorneBadRequest() throws Exception {

		doThrow(CascadeDeletionException.class).when(screenService).deleteScreenById(VALID_ID);

		mockMvc.perform(delete(SCREEN_API_URL_PATH + "/" + VALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}

}
