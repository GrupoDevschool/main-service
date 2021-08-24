package br.com.devschool.demo.infra.repository;

import br.com.devschool.demo.domain.model.internal.Version;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersionRepository extends JpaRepository<Version, Integer> {
    List<Version> findAllByProject_id(Integer projectId, Pageable pageable);
    List<Version> findByProject_id(Integer projectId);
}
