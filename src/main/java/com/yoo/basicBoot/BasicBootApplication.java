package com.yoo.basicBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BasicBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicBootApplication.class, args);
	}

}
