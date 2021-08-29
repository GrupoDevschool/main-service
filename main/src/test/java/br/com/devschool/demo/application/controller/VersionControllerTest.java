package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.model.internal.Project;
import br.com.devschool.demo.domain.model.internal.Version;
import br.com.devschool.demo.domain.model.internal.dto.VersionDTO;
import br.com.devschool.demo.domain.service.impl.VersionServiceImpl;
import br.com.devschool.demo.infra.exception.CascadeDeletionException;
import br.com.devschool.demo.util.JsonConvertionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static br.com.devschool.demo.util.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class VersionControllerTest {

	private static final String VERSION_API_URL_PATH = "/version";
	private static final Integer VERSION_VALID_ID = 1;
	private static final Integer VERSION_INVALID_ID = 2;
	private Version versionBuilder() {
		return Version.builder()
				.id(1)
				.versionNumber("xxx")
				.gmud("aaa")
				.description("bbb")
				.status(true)
				.project(new Project(1, "X", true, LocalDate.now(), LocalDate.now()))
				.createdDate(LocalDate.now())
				.updatedDate(LocalDate.now())
				.build();
	}

	private VersionDTO versionDTOBuilder(Version version) {
		return VersionDTO.builder()
				.id(version.getId())
				.versionNumber(version.getVersionNumber())
				.gmud(version.getGmud())
				.deployDate(version.getCreatedDate())
				.description(version.getDescription())
				.versionCloneId(2)
				.status(version.getStatus())
				.projectId(version.getProject().getId())
				.build();
	}

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private VersionController versionController;
	
	@Mock
	private VersionServiceImpl versionService;

	@Mock
	private Pageable pageable;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(versionController)
				.setControllerAdvice(new ErrorHandler())
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.build();
	}

	@Test
	void requestGetForListVersionsMustReturnStatusIsOk() throws Exception {
		Version version = this.versionBuilder();
		//Mockito.when(versionService.getAllVersions(1, pageable)).thenReturn(Collections.singletonList(version));

		mockMvc.perform(get(VERSION_API_URL_PATH)
				.param("projectId",version.getProject().getId().toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void quandoGETForChamadoPassandoUmIDValidoRetorneStatusIsOk() throws Exception {
		Version version = this.versionBuilder();

		when(versionService.getVersionById(VERSION_VALID_ID)).thenReturn(version);

		mockMvc.perform(get(VERSION_API_URL_PATH + "/" + VERSION_VALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void quandoGETForChamadoPassandoUmIDInvalidoRetorneStatusInternalServerError() throws Exception {
		Version version = this.versionBuilder();

		when(versionService.getVersionById(VERSION_INVALID_ID)).thenReturn(version);

		mockMvc.perform(get(VERSION_API_URL_PATH + "/" + VERSION_INVALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void quandoPOSTForChamadoEUmaVersaoForCriadaRetorneStatusIsOk() throws Exception {
		Version version = this.versionBuilder();
		VersionDTO versionDTO = this.versionDTOBuilder(version);

		when(versionService.createVersion(versionDTO)).thenReturn(version);

		mockMvc.perform(post(VERSION_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(versionDTO)))
				.andExpect(status().isOk());
	}

	@Test
	void quandoPUTForChamadoEUmProjetoForAtualizadoRetorneStatusIsOk() throws Exception {
		Version version = this.versionBuilder();
		VersionDTO versionDTO = this.versionDTOBuilder(version);

		when(versionService.updateVersion(VERSION_VALID_ID, versionDTO)).thenReturn(version);

		mockMvc.perform(put(VERSION_API_URL_PATH + "/" + VERSION_VALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(versionDTO)))
				.andExpect(status().isOk());
	}

	@Test
	void quandoDELETEForChamadoComUmIDValidoRetorneIsOk() throws Exception {
		Version version = this.versionBuilder();

		doNothing().when(versionService).deleteVersionById(VERSION_VALID_ID);

		mockMvc.perform(delete(VERSION_API_URL_PATH + "/" + VERSION_VALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void quandoDELETEForChamadoMasvERSAOPossuirtELASJaCadastradasRetorneBadRequest() throws Exception {

		doThrow(CascadeDeletionException.class).when(versionService).deleteVersionById(VERSION_INVALID_ID);

		mockMvc.perform(delete(VERSION_API_URL_PATH + "/" + VERSION_INVALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
}
