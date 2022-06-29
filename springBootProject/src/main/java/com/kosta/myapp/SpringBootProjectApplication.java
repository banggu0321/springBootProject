package com.kosta.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan ("com.kosta")
public class SpringBootProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProjectApplication.class, args);
	}

}
