package br.com.devschool.demo.infra.api;


import br.com.devschool.demo.domain.external.LoginDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("jwt")
public interface AuthAPI {

    @PostMapping("/auth")
    TokenDto getToken(LoginDTO loginDto);

    @PostMapping("/auth/user")
    User getUser(String token);

}