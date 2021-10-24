package br.com.devschool.demo.infra.repository;

import br.com.devschool.demo.domain.model.internal.RequestProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestPropertyRepository extends JpaRepository<RequestProperty, Integer> {
    List<RequestProperty> findAllByRequest_Id(Integer id);

    List<RequestProperty> findByrequestId(Integer id);
}
