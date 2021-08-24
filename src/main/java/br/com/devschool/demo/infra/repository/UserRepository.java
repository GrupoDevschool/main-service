package br.com.devschool.demo.infra.repository;

import br.com.devschool.demo.domain.model.internal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Override
    Optional<User> findById(Integer integer);

    Optional<User> findByEmail(String email);
}