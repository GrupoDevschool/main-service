package br.com.devschool.jwt.controller;

import br.com.devschool.jwt.dto.LoginDTO;
import br.com.devschool.jwt.dto.TokenDTO;
import br.com.devschool.jwt.config.security.TokenService;
import br.com.devschool.jwt.model.User;
import br.com.devschool.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TokenDTO> auth(@RequestBody @Validated LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String token = tokenService.generateToken(authentication);

        System.out.println(token);

        return ResponseEntity.ok(TokenDTO.builder()
                .type("Bearer")
                .token(token)
                .build());
    }

    @PostMapping("/user")
    public ResponseEntity<User> getUserId(@RequestBody String token) {
        if(tokenService.isTokenValido(token)) {
            Long userId = tokenService.getIdUsuario(token);
            User user = userRepository.findById(userId).get();
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().build();
    }

}
