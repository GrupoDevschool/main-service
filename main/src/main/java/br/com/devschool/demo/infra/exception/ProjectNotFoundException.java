package br.com.devschool.demo.infra.exception;

public class ProjectNotFoundException extends RuntimeException {
    private final Integer projectId;

    public ProjectNotFoundException(Integer id) {
        super(String.format("Project with id:%d not Found", id));
        this.projectId = id;
    }
}
