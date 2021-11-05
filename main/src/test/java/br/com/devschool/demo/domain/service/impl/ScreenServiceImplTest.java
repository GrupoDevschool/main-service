package br.com.devschool.demo.domain.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.devschool.demo.domain.model.internal.Screen;
import br.com.devschool.demo.domain.model.internal.Version;
import br.com.devschool.demo.domain.model.internal.dto.ScreenDTO;
import br.com.devschool.demo.infra.exception.CascadeDeletionException;
import br.com.devschool.demo.infra.exception.ScreenNotFoundException;
import br.com.devschool.demo.infra.exception.ScreensNotListedException;
import br.com.devschool.demo.infra.exception.VersionNotFoundException;
import br.com.devschool.demo.infra.repository.EventRepository;
import br.com.devschool.demo.infra.repository.ScreenRepository;
import br.com.devschool.demo.infra.repository.VersionRepository;

@ExtendWith(MockitoExtension.class)
class ScreenServiceImplTest {

	@InjectMocks
	private ScreenServiceImpl service;
	
	@Mock
	private VersionRepository versionRepository;
	@Mock
    private ScreenRepository screenRepository;
	@Mock
    private EventRepository eventRepository;
	
	@Mock
	private Screen screen;
	@Mock
	private List<Screen> screens;
	@Mock
	private ScreenDTO screenDTO;
	@Mock
	private Version version;
	@Mock
	private List<Screen> events;
	
	@Test
	void getScreensByVersionId() {
		doReturn(screens).when(screenRepository).findAllByVersion_Id(anyInt(), any());
		List<Screen> allScreens = service.getAllScreens();
		assertEquals(screens, allScreens);
	}
	
	@Test
	void whenGetScreensByVersionIdReturnEmptyThenScreensNotListedExceptionMustBeThrow() {
		doReturn(Collections.EMPTY_LIST).when(screenRepository).findAllByVersion_Id(anyInt(), any());
		assertThrows(ScreensNotListedException.class, () -> service.getAllScreens());
	}
	
	@Test
	void getScreenById() {
		doReturn(Optional.ofNullable(screen)).when(screenRepository).findById(any());
		Screen screenFinded = service.getScreenById(anyInt());
		assertEquals(screen, screenFinded);
	}
	
	@Test
	void whenGetScreenByIdThatDontExistThenMustBeThrowScreenNotFoundException() {
		doReturn(Optional.empty()).when(screenRepository).findById(any());
		assertThrows(ScreenNotFoundException.class, () -> service.getScreenById(anyInt()));
	}

	@Test
	void createScreenWithoutFather() {
		doReturn(Optional.ofNullable(version)).when(versionRepository).findById(anyInt());
		doReturn(null).when(screenDTO).getScreenFatherId();
		service.createScreen(screenDTO);
		Mockito.verify(screenRepository).save(any());
	}
	
	@Test
	void createScreenWithFather() {
		doReturn(Optional.ofNullable(version)).when(versionRepository).findById(anyInt());
		doReturn(Optional.ofNullable(screen)).when(screenRepository).findById(anyInt());
		service.createScreen(screenDTO);
		Mockito.verify(screenRepository).save(any());
	}
	
	@Test
	void createScreenOfVersionThatDontExistMustThrowVersionNotFoundException() {
		doReturn(Optional.empty()).when(versionRepository).findById(anyInt());
		assertThrows(VersionNotFoundException.class, () -> service.createScreen(screenDTO));
	}
	
	@Test
	void createScreenWithFatherThatDontExistMustThrowScreenNotFoundException() {
		doReturn(Optional.ofNullable(version)).when(versionRepository).findById(anyInt());
		doReturn(Optional.empty()).when(screenRepository).findById(anyInt());
		assertThrows(ScreenNotFoundException.class, () -> service.createScreen(screenDTO));
	}
	
	@Test
	void updateScreen() {
		doReturn(Optional.ofNullable(screen)).when(screenRepository).findById(anyInt());
		doReturn(Optional.ofNullable(version)).when(versionRepository).findById(anyInt());
		service.updateScreen(anyInt(), screenDTO);
		Mockito.verify(screenRepository).save(any());
	}
	
	@Test
	void tryToUpdateScreenThatDontExistMustThrowScreenNotFoundException() {
		doReturn(Optional.empty()).when(screenRepository).findById(anyInt());
		assertThrows(ScreenNotFoundException.class, () -> service.updateScreen(anyInt(), screenDTO));
	}
	
	@Test
	void tryToUpdateScreenToVersionThatDontExistMustThrowVersionNotFoundException() {
		doReturn(Optional.ofNullable(screen)).when(screenRepository).findById(anyInt());
		doReturn(Optional.empty()).when(versionRepository).findById(anyInt());
		assertThrows(VersionNotFoundException.class, () -> service.updateScreen(anyInt(), screenDTO));
	}
	
	@Test
	void deleteScreenById() {
		doReturn(Optional.ofNullable(screen)).when(screenRepository).findById(anyInt());
		doReturn(Collections.EMPTY_LIST).when(eventRepository).findAllByScreen_Id(anyInt());
		doReturn(Collections.EMPTY_LIST).when(screenRepository).findAllByscreenFather_Id(anyInt());
		service.deleteScreenById(anyInt());
		Mockito.verify(screenRepository).deleteById(anyInt());
	}
	
	@Test
	void tryDeleteAScreenThatDontExistMustThrowScreenNotFoundException() {
		doReturn(Optional.empty()).when(screenRepository).findById(anyInt());
		assertThrows(ScreenNotFoundException.class, () -> service.deleteScreenById(anyInt()));
	}
	
	@Test
	void tryDeleteAScreenWithEventsAssociatedMustThrowCascadeDeletionException() {
		doReturn(Optional.ofNullable(screen)).when(screenRepository).findById(anyInt());
		doReturn(events).when(eventRepository).findAllByScreen_Id(anyInt());
		assertThrows(CascadeDeletionException.class, () -> service.deleteScreenById(anyInt()));
	}
	
	@Test
	void tryDeleteAScreenWithScreensAssociatedMustThrowCascadeDeletionException() {
		doReturn(Optional.ofNullable(screen)).when(screenRepository).findById(anyInt());
		doReturn(Collections.EMPTY_LIST).when(eventRepository).findAllByScreen_Id(anyInt());
		doReturn(screens).when(screenRepository).findAllByscreenFather_Id(anyInt());
		assertThrows(CascadeDeletionException.class, () -> service.deleteScreenById(anyInt()));
	}
}
