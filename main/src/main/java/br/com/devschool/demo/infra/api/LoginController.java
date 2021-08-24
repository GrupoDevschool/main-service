package br.com.devschool.demo.infra.api;

import br.com.devschool.demo.domain.external.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<TokenDto> getToken(@RequestBody LoginDTO loginDto) {
        return ResponseEntity.ok(loginService.getToken(loginDto));
    }

}
