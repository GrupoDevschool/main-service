package br.com.devschool.demo.infra.exception;

public class RequestNotFoundException extends RuntimeException {
    private final Integer requestId;

    public RequestNotFoundException(Integer id) {
        super(String.format("Request with id:%d not Found", id));
        this.requestId = id;
    }
}
