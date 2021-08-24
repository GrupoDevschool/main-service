package br.com.devschool.demo.infra.exception;

public class VersionNotFoundException extends RuntimeException {
    private final Integer versionId;

    public VersionNotFoundException(Integer id) {
        super(String.format("Version with id:%d not Found", id));
        this.versionId = id;
    }
}
