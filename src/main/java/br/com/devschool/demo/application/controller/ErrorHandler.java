package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.model.internal.dto.ReturnMessage;
import br.com.devschool.demo.infra.exception.*;
import br.com.devschool.demo.infra.util.log.Logger;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {
    private final Logger logger;

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ReturnMessage> projectNotFoundException(ProjectNotFoundException e) {
        logger.log(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ReturnMessage.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(VersionNotFoundException.class)
    public ResponseEntity<ReturnMessage> versionNotFoundException(VersionNotFoundException e) {
        logger.log(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ReturnMessage.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(ScreenNotFoundException.class)
    public ResponseEntity<ReturnMessage> versionNotFoundException(ScreenNotFoundException e) {
        logger.log(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ReturnMessage.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ReturnMessage> versionNotFoundException(EventNotFoundException e) {
        logger.log(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ReturnMessage.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(EventTypeNotFoundException.class)
    public ResponseEntity<ReturnMessage> versionNotFoundException(EventTypeNotFoundException e) {
        logger.log(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ReturnMessage.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<ReturnMessage> versionNotFoundException(RequestNotFoundException e) {
        logger.log(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ReturnMessage.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(RequestPropertyNotFoundException.class)
    public ResponseEntity<ReturnMessage> versionNotFoundException(RequestPropertyNotFoundException e) {
        logger.log(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ReturnMessage.builder().message(e.getMessage()).build());
    }



    @ExceptionHandler(ProjectsNotListedException.class)
    public ResponseEntity<ReturnMessage> projectsNotListedException() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(VersionsNotListedException.class)
    public ResponseEntity<ReturnMessage> versionsNotListedException() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(ScreensNotListedException.class)
    public ResponseEntity<ReturnMessage> screensNotListedException() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(CascadeDeletionException.class)
    public ResponseEntity<ReturnMessage> CascadeDeletionException(CascadeDeletionException e) {
        logger.log(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ReturnMessage.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(FeignException.BadRequest.class)
    public ResponseEntity<ReturnMessage> invalidLoginException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ReturnMessage.builder().message("Não foi possível validar esse acesso.").build());
    }
}
