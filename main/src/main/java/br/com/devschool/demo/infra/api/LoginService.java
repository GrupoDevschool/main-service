package br.com.devschool.demo.infra.api;

import br.com.devschool.demo.domain.external.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AuthAPI authAPI;

    public TokenDto getToken(LoginDTO loginDto) {
        return authAPI.getToken(loginDto);
    }

}
