package br.com.devschool.demo.infra.exception;

public class EventTypeNotFoundException extends RuntimeException {
    private final Integer eventTypeId;

    public EventTypeNotFoundException(Integer id) {
        super(String.format("EventType with id:%d not Found", id));
        this.eventTypeId = id;
    }
}
