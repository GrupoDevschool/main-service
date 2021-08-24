package br.com.devschool.demo.domain.service.impl;

import br.com.devschool.demo.domain.external.LoginConfirmDTO;
import br.com.devschool.demo.domain.external.LoginDTO;
import br.com.devschool.demo.domain.service.LoginService;
import br.com.devschool.demo.infra.api.ValidationAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final ValidationAPI validationAPI;

    @Override
    public LoginConfirmDTO authenticateUser(LoginDTO loginDTO) {
        return validationAPI.authenticateUser(loginDTO);
    }
}
