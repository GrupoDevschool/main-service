package br.com.devschool.demo.infra.exception;

public class ScreenNotFoundException extends RuntimeException {
    private final Integer screenId;

    public ScreenNotFoundException(Integer id) {
        super(String.format("Screen with id:%d not Found", id));
        this.screenId = id;
    }
}