package br.com.devschool.demo.infra.exception;

public class CascadeDeletionException extends RuntimeException {

    public CascadeDeletionException(String message) {
        super(message);
    }
}
