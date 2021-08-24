
package br.com.devschool.demo.infra.repository;

import br.com.devschool.demo.domain.model.internal.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Integer> { }
