package br.com.devschool.demo.domain.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.devschool.demo.domain.model.internal.Project;
import br.com.devschool.demo.domain.model.internal.Version;
import br.com.devschool.demo.infra.exception.CascadeDeletionException;
import br.com.devschool.demo.infra.exception.ProjectNotFoundException;
import br.com.devschool.demo.infra.exception.ProjectsNotListedException;
import br.com.devschool.demo.infra.repository.ProjectRepository;
import br.com.devschool.demo.infra.repository.VersionRepository;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

	@InjectMocks
	private ProjectServiceImpl service;

	@Mock
	private ProjectRepository projectRepository;
	@Mock
	private VersionRepository versionRepository;

	@Captor
	private ArgumentCaptor<Project> captor;

	private Project mockProject() {
		return Project.builder().id(1).name("Example").status(true).build();
	}

	private List<Project> mockListProjects() {
		List<Project> projects = new ArrayList<>();
		projects.add(mockProject());
		return projects;
	}

	@Test
	void getAllProjectsByStatus() {
		doReturn(mockListProjects()).when(projectRepository).findAllByNameStartingWithAndStatus(anyString(),
				anyBoolean(), any());
		service.getAllProjects("", "true", null);

		Mockito.verify(projectRepository).findAllByNameStartingWithAndStatus(anyString(), anyBoolean(), any());
	}

	@Test
	void getAllProjectsByName() {
		doReturn(mockListProjects()).when(projectRepository).findAllByNameStartingWith(anyString(), any());
		service.getAllProjects("", null, null);

		Mockito.verify(projectRepository).findAllByNameStartingWith(any(), any());
	}

	@Test
	void whenGetAllProjectsReturnEmptyListMustThrowProjectsNotListedException() {
		doReturn(new ArrayList<Project>()).when(projectRepository).findAllByNameStartingWith(anyString(), any());
		Assertions.assertThrows(ProjectsNotListedException.class, () -> service.getAllProjects("", null, null));
	}

	@Test
	void getProjectById() {
		doReturn(Optional.ofNullable(mockProject())).when(projectRepository).findById(anyInt());
		Project projectById = service.getProjectById(anyInt());
		Assertions.assertEquals(mockProject(), projectById);
	}

	@Test
	void whenIdIsNotFoundInGetProjectsByIdMustThrowProjectNotFoundException() {
		doReturn(Optional.ofNullable(null)).when(projectRepository).findById(anyInt());
		Assertions.assertThrows(ProjectNotFoundException.class, () -> service.getProjectById(anyInt()));
	}

	@Test
	void createProject() {
		Project project = mockProject();
		service.createProject(project);
		Mockito.verify(projectRepository).save(captor.capture());
		Project newProject = captor.getValue();

		assertEquals(project.getName(), newProject.getName());
		assertEquals(project.isStatus(), newProject.isStatus());
	}

	@Test
	void updateProject() {
		Project project = mockProject();
		Project toUpdateProject = Project.builder().name("modified").status(false).build();
		doReturn(Optional.ofNullable(project)).when(projectRepository).findById(anyInt());

		service.updateProject(anyInt(), toUpdateProject);
		Mockito.verify(projectRepository).save(captor.capture());
		Project updatedProject = captor.getValue();

		assertEquals(project.getId(), updatedProject.getId());
		assertEquals(toUpdateProject.getName(), updatedProject.getName());
		assertEquals(toUpdateProject.isStatus(), updatedProject.isStatus());
		assertEquals(project.getCreatedDate(), updatedProject.getCreatedDate());
		assertNotEquals(project.getUpdatedDate(), updatedProject.getUpdatedDate());
	}

	@Test
	void tryUpdateProjectThatNotExistMustThrowProjectNotFoundException() {
		Project toUpdateProject = Project.builder().name("modified").status(false).build();
		doReturn(Optional.ofNullable(null)).when(projectRepository).findById(anyInt());

		assertThrows(ProjectNotFoundException.class, () -> service.updateProject(anyInt(), toUpdateProject));
	}

	@Test
	void deleteProjectById() {
		doReturn(Optional.ofNullable(mockProject())).when(projectRepository).findById(any());
		doReturn(new ArrayList<Version>()).when(versionRepository).findByProject_id(any());

		service.deleteProjectById(any());
		Mockito.verify(projectRepository).deleteById(any());
	}

	@Test
	void tryDeleteProjectThatNotExistMustThrowProjectNotFoundException() {
		doReturn(Optional.ofNullable(null)).when(projectRepository).findById(any());

		assertThrows(ProjectNotFoundException.class, () -> service.deleteProjectById(any()));
	}

	@Test
	void tryDeleteProjectThatHaveVersionsAssiciatedMustThrowProjectNotFoundException() {
		doReturn(Optional.ofNullable(mockProject())).when(projectRepository).findById(any());
		List<Version> versions = new ArrayList<>();
		versions.add(new Version());
		doReturn(versions).when(versionRepository).findByProject_id(any());

		assertThrows(CascadeDeletionException.class, () -> service.deleteProjectById(any()));
	}

}
