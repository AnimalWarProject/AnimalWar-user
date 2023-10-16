package com.example.aniamlwaruser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AniamlwaruserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AniamlwaruserApplication.class, args);
	}

}
