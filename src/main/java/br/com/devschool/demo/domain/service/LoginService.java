package br.com.devschool.demo.domain.service;

import br.com.devschool.demo.domain.external.LoginConfirmDTO;
import br.com.devschool.demo.domain.external.LoginDTO;

public interface LoginService {
    LoginConfirmDTO authenticateUser(LoginDTO loginDTO);
}
