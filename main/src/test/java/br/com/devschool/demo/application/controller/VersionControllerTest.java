package br.com.devschool.demo.application.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.devschool.demo.domain.model.internal.Version;
import br.com.devschool.demo.domain.model.internal.dto.VersionDTO;
import br.com.devschool.demo.domain.service.impl.VersionServiceImpl;

@ExtendWith(MockitoExtension.class)
class VersionControllerTest {

	@InjectMocks
	private VersionController controller;
	
	@Mock
	private VersionServiceImpl service;
	@Mock(answer = Answers.RETURNS_MOCKS)
	private Version version;

	@Test
	void requestGetForVersionsMustReturnStatusOk() {
		ResponseEntity<List<VersionDTO>> results = controller.getAllVersions(any(), any());
		Assertions.assertEquals(HttpStatus.OK, results.getStatusCode());
	}
	
	@Test
	void requestGetVersionByIdMustReturnStatusOk() {
		doReturn(version).when(service).getVersionById(any());
		ResponseEntity<VersionDTO> result = controller.getVersionById(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void requestCreateVersionMustReturnStatusOk() {
		ResponseEntity<Version> result = controller.createVersion(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void requestUpdateVersionMustReturnStatusOk() {
		ResponseEntity<Version> result = controller.updateVersion(any(), any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void requestdeleteVersionMustReturnStatusOk() {
		ResponseEntity<Version> result = controller.deleteVersion(any());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
}
