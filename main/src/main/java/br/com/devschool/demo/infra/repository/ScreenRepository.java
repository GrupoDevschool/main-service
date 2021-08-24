package br.com.devschool.demo.infra.repository;

import br.com.devschool.demo.domain.model.internal.Screen;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer> {
    List<Screen> findAllByVersion_Id(Integer versionId, Pageable pageable);
    List<Screen> findByVersion_Id(Integer versionId);
    List<Screen> findAllByscreenFather_Id(Integer screenFatherId);
}
