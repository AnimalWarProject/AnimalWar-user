package com.example.aniamlwaruser;

import com.example.aniamlwaruser.service.AnimalService;
import com.example.aniamlwaruser.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
@RequiredArgsConstructor
public class AniamlwaruserApplication {
	private final AnimalService animalService;
	private final BuildingService buildingService;
	public static void main(String[] args) {
		SpringApplication.run(AniamlwaruserApplication.class, args);
	}

	@Bean
	public void saveAnimals() throws ExecutionException, InterruptedException {
		animalService.saveAnimals();
	};

	@Bean
	public void saveBuildings(){
		buildingService.saveBuildings();
	};
}
