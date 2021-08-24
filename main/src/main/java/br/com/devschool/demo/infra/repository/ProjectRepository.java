package br.com.devschool.demo.infra.repository;

import br.com.devschool.demo.domain.model.internal.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findAllByNameStartingWithAndStatus(String name, boolean status, Pageable pageable);

    List<Project> findAllByNameStartingWith(String name, Pageable pageable);
}
