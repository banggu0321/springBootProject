package com.kosta.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan ("com.kosta") //@VO에 작성한 Entity를 스캔
//@EnableJpaRepositories("com.kosta.myapp.repository") 
//기본 패키지가 아닌 곳에 repository를 스캔해야 하는 경우 필요

public class SpringBootProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProjectApplication.class, args);
	}

}
