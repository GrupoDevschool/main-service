package br.com.devschool.demo.infra.api;

import br.com.devschool.demo.domain.external.LoginConfirmDTO;
import br.com.devschool.demo.domain.external.LoginDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "validation-api", url = "${application.validation-api.url}")
public interface ValidationAPI {

    @PostMapping()
    LoginConfirmDTO authenticateUser(@RequestBody LoginDTO loginDTO);

}