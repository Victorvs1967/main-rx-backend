package com.vvs.mainrxbackend;

import com.github.cloudyrock.spring.v5.EnableMongock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableMongock
@EnableWebFlux
public class MainRxBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainRxBackendApplication.class, args);
	}

}
