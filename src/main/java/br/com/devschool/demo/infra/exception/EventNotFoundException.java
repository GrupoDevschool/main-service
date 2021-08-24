package br.com.devschool.demo.infra.exception;

public class EventNotFoundException extends RuntimeException {
    private final Integer eventId;

    public EventNotFoundException(Integer id) {
        super(String.format("Event with id:%d not Found", id));
        this.eventId = id;
    }
}