package br.com.devschool.demo.infra.exception;

public class RequestPropertyNotFoundException extends RuntimeException {
    private final Integer requestPropertyId;

    public RequestPropertyNotFoundException(Integer id) {
        super(String.format("RequestProperty with id:%d not Found", id));
        this.requestPropertyId = id;
    }
}
