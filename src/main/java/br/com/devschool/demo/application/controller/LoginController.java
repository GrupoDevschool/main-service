package br.com.devschool.demo.application.controller;

import br.com.devschool.demo.domain.external.LoginConfirmDTO;
import br.com.devschool.demo.domain.external.LoginDTO;
import br.com.devschool.demo.domain.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/session")
    ResponseEntity<LoginConfirmDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(loginService.authenticateUser(loginDTO));
    }
}
