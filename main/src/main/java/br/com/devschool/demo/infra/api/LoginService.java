package br.com.devschool.demo.infra.api;

import br.com.devschool.demo.domain.external.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthAPI authAPI;

    public TokenDto getToken(LoginDTO loginDto) {
        return authAPI.getToken(loginDto);
    }

}
