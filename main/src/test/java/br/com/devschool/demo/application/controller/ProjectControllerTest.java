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

import br.com.devschool.demo.domain.model.internal.Project;
import br.com.devschool.demo.domain.service.ProjectService;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

	@InjectMocks
	private ProjectController controller;
	
	@Mock
	private ProjectService projectService;
	
	@Test
	void requestGetForProjectsMustReturnStatusOk() {
		ResponseEntity<List<Project>> results = controller.getAllProjects(any(), any(), any());
		Assertions.assertEquals(HttpStatus.OK, results.getStatusCode());
	}
	
	@Test
	void requestGetProjectByIdMustReturnStatusOk() {
		ResponseEntity<Project> result = controller.getProjectById(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void requestCreateProjectMustReturnStatusOk() {
		ResponseEntity<Project> result = controller.createProject(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void requestUpdateProjectMustReturnStatusOk() {
		ResponseEntity<Project> result = controller.updateProject(any(), any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void requestdeleteProjectMustReturnStatusOk() {
		ResponseEntity<Project> result = controller.deleteProject(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

}
