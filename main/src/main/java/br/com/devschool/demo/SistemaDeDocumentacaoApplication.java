package br.com.devschool.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableCaching
@EnableFeignClients
public class SistemaDeDocumentacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeDocumentacaoApplication.class, args);
	}

}
