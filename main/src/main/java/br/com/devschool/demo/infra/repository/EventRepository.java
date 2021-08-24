package br.com.devschool.demo.infra.repository;

import br.com.devschool.demo.domain.model.internal.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllByScreen_Id(Integer id);
}
