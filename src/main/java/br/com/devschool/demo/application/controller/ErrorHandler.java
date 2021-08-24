package br.com.devschool.demo.application.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.devschool.demo.domain.model.internal.dto.ReturnMessage;
import br.com.devschool.demo.infra.exception.CascadeDeletionException;
import br.com.devschool.demo.infra.exception.EventNotFoundException;
import br.com.devschool.demo.infra.exception.EventTypeNotFoundException;
import br.com.devschool.demo.infra.exception.ProjectNotFoundException;
import br.com.devschool.demo.infra.exception.ProjectsNotListedException;
import br.com.devschool.demo.infra.exception.RequestNotFoundException;
import br.com.devschool.demo.infra.exception.RequestPropertyNotFoundException;
import br.com.devschool.demo.infra.exception.ScreenNotFoundException;
import br.com.devschool.demo.infra.exception.ScreensNotListedException;
import br.com.devschool.demo.infra.exception.VersionNotFoundException;
import br.com.devschool.demo.infra.exception.VersionsNotListedException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
    
    private static void log(RuntimeException e, HttpServletRequest request) {
    	logger.info(e.getMessage(), request.getRequestURI(), request.getMethod());
    }

    private static void log(String message, HttpServletRequest request) {
    	logger.info(message, request.getRequestURI(), request.getMethod());
    }
    
    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ReturnMessage> projectNotFoundException(ProjectNotFoundException e, HttpServletRequest request) {
        log(e, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ReturnMessage.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(VersionNotFoundException.class)
    public ResponseEntity<ReturnMessage> versionNotFoundException(VersionNotFoundException e, HttpServletRequest request) {
    	log(e, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ReturnMessage.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(ScreenNotFoundException.class)
    public ResponseEntity<ReturnMessage> versionNotFoundException(ScreenNotFoundException e, HttpServletRequest request) {
    	log(e, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ReturnMessage.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ReturnMessage> versionNotFoundException(EventNotFoundException e, HttpServletRequest request) {
    	log(e, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ReturnMessage.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(EventTypeNotFoundException.class)
    public ResponseEntity<ReturnMessage> versionNotFoundException(EventTypeNotFoundException e, HttpServletRequest request) {
    	log(e, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ReturnMessage.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<ReturnMessage> versionNotFoundException(RequestNotFoundException e, HttpServletRequest request) {
    	log(e, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ReturnMessage.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(RequestPropertyNotFoundException.class)
    public ResponseEntity<ReturnMessage> versionNotFoundException(RequestPropertyNotFoundException e, HttpServletRequest request) {
    	log(e, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ReturnMessage.builder().message(e.getMessage()).build());
    }



    @ExceptionHandler(ProjectsNotListedException.class)
    public ResponseEntity<ReturnMessage> projectsNotListedException(HttpServletRequest request) {
    	log("Nenhum projeto cadastrado", request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(VersionsNotListedException.class)
    public ResponseEntity<ReturnMessage> versionsNotListedException(HttpServletRequest request) {
    	log("Nenhuma versão cadastrada", request);
    	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(ScreensNotListedException.class)
    public ResponseEntity<ReturnMessage> screensNotListedException(HttpServletRequest request) {
    	log("Nenhuma tela cadastrada", request);
    	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(CascadeDeletionException.class)
    public ResponseEntity<ReturnMessage> CascadeDeletionException(CascadeDeletionException e, HttpServletRequest request) {
        log(e, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ReturnMessage.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(FeignException.BadRequest.class)
    public ResponseEntity<ReturnMessage> invalidLoginException(HttpServletRequest request) {
    	String message = "Não foi possível validar esse acesso.";
    	log(message, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ReturnMessage.builder().message(message).build());
    }
}
