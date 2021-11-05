package br.com.devschool.demo.infra.api;

import br.com.devschool.demo.domain.external.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<TokenDto> getToken(@RequestBody LoginDTO loginDto) {
        return ResponseEntity.ok(loginService.getToken(loginDto));
    }

}
