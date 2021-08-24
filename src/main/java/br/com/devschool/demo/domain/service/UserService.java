package br.com.devschool.demo.domain.service;

import br.com.devschool.demo.domain.model.internal.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findUserId(Integer id);

    User createUser(User user);

    User updateUser(Integer id, User user);

    void deleteUserId(Integer id);

}