package br.com.devschool.demo.domain.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import br.com.devschool.demo.domain.model.internal.Screen;
import br.com.devschool.demo.domain.model.internal.Version;
import br.com.devschool.demo.domain.model.internal.dto.VersionDTO;
import br.com.devschool.demo.infra.exception.CascadeDeletionException;
import br.com.devschool.demo.infra.exception.ProjectNotFoundException;
import br.com.devschool.demo.infra.exception.VersionNotFoundException;
import br.com.devschool.demo.infra.exception.VersionsNotListedException;
import br.com.devschool.demo.infra.repository.ProjectRepository;
import br.com.devschool.demo.infra.repository.ScreenRepository;
import br.com.devschool.demo.infra.repository.VersionRepository;

@ExtendWith(MockitoExtension.class)
class VersionServiceImplTest {

	@InjectMocks
	private VersionServiceImpl service;

	@Mock
	private ProjectRepository projectRepository;
	@Mock
	private VersionRepository versionRepository;
	@Mock
	private ScreenRepository screenRepository;

	@Mock
	private Version version;
	@Mock
	private VersionDTO versionDTO;
	@Mock
	private List<Project> versions;
	@Mock
	private Project project;
	@Mock
	private List<Screen> screens;

	@Captor
	private ArgumentCaptor<Version> captor;

//	private Version mockVersion() {
//		return Version.builder().id(1).versionNumber("1").gmud("1").description("example").deployDate(LocalDate.now())
//				.status(true).order(1).build();
//	}
//
//	private VersionDTO mockVersionDTO() {
//		return VersionDTO.builder().id(1).versionNumber("1").gmud("1").description("example")
//				.deployDate(LocalDate.now()).status(true).order(1).projectId(1).build();
//	}
//
//	private List<Version> mockListVersion() {
//		List<Version> versions = new ArrayList<>();
//		versions.add(mockVersion());
//		return versions;
//	}

	@Test
	void getAllVersions() {
		doReturn(versions).when(versionRepository).findAllByProject_id(any(), any());
		List<Version> versions = service.getAllVersions(any(), any());
		assertTrue(!versions.isEmpty());
	}

	@Test
	void whenGetAllVersionsReturnEmptyListMustThrowVersionNotListedException() {
		doReturn(new ArrayList<Version>()).when(versionRepository).findAllByProject_id(any(), any());
		Assertions.assertThrows(VersionsNotListedException.class, () -> service.getAllVersions(any(), any()));
	}

	@Test
	void getVersionById() {
		doReturn(Optional.ofNullable(version)).when(versionRepository).findById(anyInt());
		Version versionById = service.getVersionById(anyInt());
		Assertions.assertEquals(version, versionById);
	}

	@Test
	void whenIdIsNotFoundInGetVersionsByIdMustThrowVersionNotFoundException() {
		doReturn(Optional.ofNullable(null)).when(versionRepository).findById(anyInt());
		Assertions.assertThrows(VersionNotFoundException.class, () -> service.getVersionById(anyInt()));
	}

	@Test
	void createVersion() {
		doReturn(Optional.ofNullable(project)).when(projectRepository).findById(any());
		doReturn(version).when(versionRepository).save(any());
		doReturn(null).when(versionDTO).getVersionCloneId();
		service.createVersion(versionDTO); 
		Mockito.verify(versionRepository).save(any());
	}

	@Test
	void tryCreateAVersionOfInexistentProjectMustThrowProjectNotFoundException() {
		doReturn(Optional.ofNullable(null)).when(projectRepository).findById(any());
		assertThrows(ProjectNotFoundException.class, () -> service.createVersion(versionDTO));
	}

	@Test
	void updateVersion() {
		doReturn(Optional.ofNullable(version)).when(versionRepository).findById(any());
		doReturn(Optional.ofNullable(project)).when(projectRepository).findById(any());
		service.updateVersion(anyInt(), versionDTO);
		Mockito.verify(versionRepository).save(any());
	}

	@Test
	void tryUpdateVersionThatNotExistsMustThrowVersionNotFoundException() {
		doReturn(Optional.ofNullable(null)).when(versionRepository).findById(any());
		assertThrows(VersionNotFoundException.class, () -> service.updateVersion(any(), versionDTO));
	}
	
	@Test
	void tryUpdateVersionToProjectThatNotExistMustThrowProjectNotFoundException() {
		doReturn(Optional.ofNullable(version)).when(versionRepository).findById(any());
		doReturn(Optional.ofNullable(null)).when(projectRepository).findById(any());
		assertThrows(ProjectNotFoundException.class, () -> service.updateVersion(any(), versionDTO));
	}
	
	@Test
	void deleteById() {
		doReturn(Optional.ofNullable(version)).when(versionRepository).findById(anyInt());
		doReturn(screens).when(screenRepository).findByVersion_Id(anyInt());
		doReturn(true).when(screens).isEmpty();
		service.deleteVersionById(anyInt());
		Mockito.verify(versionRepository).deleteById(anyInt());
	}
	
	@Test
	void tryDeleteAVersionThatDontExistsMustThrowVersionNotFoundException() {
		doReturn(Optional.ofNullable(null)).when(versionRepository).findById(anyInt());
		assertThrows(VersionNotFoundException.class, () -> service.deleteVersionById(anyInt()));
	}
	
	@Test
	void tryDeleteAVersionThatHasScreensAssociatedMustThrowCascadeDeletionException() {
		doReturn(Optional.ofNullable(version)).when(versionRepository).findById(anyInt());
		doReturn(screens).when(screenRepository).findByVersion_Id(anyInt());
		assertThrows(CascadeDeletionException.class, () -> service.deleteVersionById(anyInt()));
	}
}
