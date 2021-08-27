package br.com.devschool.demo.application.controller;

import static br.com.devschool.demo.util.JsonConvertionUtils.asJsonString;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import br.com.devschool.demo.infra.exception.ProjectNotFoundException;
import br.com.devschool.demo.util.JsonConvertionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	private static final Project project = Project.builder()
			.id(1)
			.name("Projeto Test")
			.status(true)
			.createdDate(LocalDate.now())
			.updatedDate(LocalDate.now())
			.build();

	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@InjectMocks
	private ProjectController projectController;
	
	@Mock
	private ProjectService projectService;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(projectController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers((s, locale) -> new MappingJackson2JsonView())
				.build();
	}
	
	@Test
	void requestGetForListProjectsMustReturnStatusOk() throws Exception {

		//when
		when(projectService.getAllProjects(any(), any(), any())).thenReturn(Collections.singletonList(project));

		//then
		mockMvc.perform(get(PROJECT_API_URL_PATH)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void quandoGETForChamadoPassandoUmIDValidoRetorneStatusIsOk() throws Exception {
		//when
		when(projectService.getProjectById(PROJECT_VALID_ID)).thenReturn(project);

		//then
		mockMvc.perform(get(PROJECT_API_URL_PATH + "/" + PROJECT_VALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void quandoGETForChamadoPassandoUmIDInvalidoRetorneStatusNotFound() throws Exception {

	}

	@Test
	void requestCreateProjectMustReturnStatusOk() {
		ResponseEntity<Project> result = projectController.createProject(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void requestUpdateProjectMustReturnStatusOk() {
		ResponseEntity<Project> result = projectController.updateProject(any(), any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void requestdeleteProjectMustReturnStatusOk() {
		ResponseEntity<Project> result = projectController.deleteProject(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

}
