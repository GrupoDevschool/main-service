package br.com.devschool.demo.infra.repository;

import br.com.devschool.demo.domain.model.internal.Request;
import br.com.devschool.demo.domain.model.internal.RequestProperty;
import br.com.devschool.demo.domain.model.internal.dto.RequestPropertyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findAllByEventId(Integer eventId);

    List<Request> getAllByEvent_Id(Integer originalEventId);

}
