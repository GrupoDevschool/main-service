package br.com.devschool.demo.application.controller;

import static br.com.devschool.demo.util.JsonConvertionUtils.asJsonString;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import br.com.devschool.demo.infra.exception.CascadeDeletionException;
import br.com.devschool.demo.infra.exception.ProjectNotFoundException;
import br.com.devschool.demo.util.JsonConvertionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.AbstractCharSequenceAssert;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.devschool.demo.domain.model.internal.Project;
import br.com.devschool.demo.domain.service.ProjectService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

	private static final String PROJECT_API_URL_PATH = "/project";
	private static final Integer PROJECT_VALID_ID = 1;
	private static final Integer PROJECT_INVALID_ID = 2;
	private Project projectBuilder() {
		return Project.builder()
				.id(1)
				.name("Projeto Test")
				.status(true)
				.createdDate(LocalDate.now())
				.updatedDate(LocalDate.now())
				.build();
	}

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private ProjectController projectController;
	
	@Mock
	private ProjectService projectService;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(projectController)
				.setControllerAdvice(new ErrorHandler())
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.build();
	}
	
	@Test
	void requestGetForListProjectsMustReturnStatusOk() throws Exception {
		Project expectedProject = this.projectBuilder();

		//when
		when(projectService.getAllProjects(any(), any(), any())).thenReturn(Collections.singletonList(expectedProject));

		//then
		mockMvc.perform(get(PROJECT_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void quandoGETForChamadoPassandoUmIDValidoRetorneStatusIsOk() throws Exception {
		Project expectedProject = this.projectBuilder();

		//when
		when(projectService.getProjectById(PROJECT_VALID_ID)).thenReturn(expectedProject);

		//then
		mockMvc.perform(get(PROJECT_API_URL_PATH + "/" + PROJECT_VALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(expectedProject.getName())))
				.andExpect(jsonPath("$.active", is(expectedProject.isStatus())));

	}

	@Test
	void quandoGETForChamadoPassandoUmIDInvalidoRetorneStatusInternalServerError() throws Exception {
		//when
		when(projectService.getProjectById(PROJECT_INVALID_ID)).thenThrow(ProjectNotFoundException.class);

		//then
		mockMvc.perform(get(PROJECT_API_URL_PATH + "/" + PROJECT_INVALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}

	@Test
	void quandoPOSTForChamadoEUmProjetoForCriadoRetorneStatusIsOk() throws Exception {
		Project project = this.projectBuilder();

		when(projectService.createProject(project)).thenReturn(project);

		mockMvc.perform(post(PROJECT_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(project)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(project.getName())));
	}

	/*@Test
	void quandoPOSTForChamadoEUmCampoForNuloRetorneStatusInternalServerError() throws Exception {
		Project project = this.projectBuilder();
		project.setName(null);

		mockMvc.perform(post(PROJECT_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(project)))
				.andExpect(status().isInternalServerError());
	}*/
	
	@Test
	void quandoPUTForChamadoEUmProjetoForAtualizadoRetorneStatusIsOk() throws Exception {
		Project project = this.projectBuilder();

		when(projectService.updateProject(PROJECT_VALID_ID, project)).thenReturn(project);

		mockMvc.perform(put(PROJECT_API_URL_PATH + "/" + PROJECT_VALID_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(project)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(project.getName())));
	}
	
	@Test
	void quandoDELETEForChamadoComUmIDValidoRetorneIsOk() throws Exception {
		Project project = this.projectBuilder();

		doNothing().when(projectService).deleteProjectById(project.getId());

		mockMvc.perform(delete(PROJECT_API_URL_PATH + "/" + project.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void quandoDELETEForChamadoComUmIDInvalidoRetorneBadRequest() throws Exception {

		doThrow(ProjectNotFoundException.class).when(projectService).deleteProjectById(PROJECT_INVALID_ID);

		mockMvc.perform(delete(PROJECT_API_URL_PATH + "/" + PROJECT_INVALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}

	@Test
	void quandoDELETEForChamadoMasProjetoPossuirVersoesJaCadastradasRetorneBadRequest() throws Exception {

		doThrow(CascadeDeletionException.class).when(projectService).deleteProjectById(PROJECT_VALID_ID);

		mockMvc.perform(delete(PROJECT_API_URL_PATH + "/" + PROJECT_VALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

}
