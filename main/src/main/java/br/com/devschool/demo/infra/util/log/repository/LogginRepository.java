package br.com.devschool.demo.infra.util.log.repository;

import br.com.devschool.demo.infra.util.log.internal.Logging;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogginRepository extends CrudRepository<Logging, Long> {

}
