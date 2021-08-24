package br.com.devschool.demo.infra.repository;

import br.com.devschool.demo.domain.model.internal.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> getAllByEvent_Id(Integer eventId);
}
