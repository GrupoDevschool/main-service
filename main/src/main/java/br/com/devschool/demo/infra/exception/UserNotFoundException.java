package br.com.devschool.demo.infra.exception;

public class UserNotFoundException extends RuntimeException {
    private final Integer userId;

    public UserNotFoundException(Integer id) {
        super(String.format("User with id:%d not Found", id));
        this.userId = id;
    }
}
