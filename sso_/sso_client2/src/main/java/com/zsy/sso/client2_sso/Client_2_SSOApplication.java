package com.zsy.sso.client2_sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@EnableOAuth2Sso
@SpringBootApplication
@RestController
public class Client_2_SSOApplication {
	@GetMapping("/user")
	public Authentication user(Authentication user) {
		return user;
	}
	public static void main(String[] args) {
		SpringApplication.run(Client_2_SSOApplication.class, args);
	}
}
