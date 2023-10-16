package com.example.aniamlwaruser.client.api;

import com.example.aniamlwaruser.domain.dto.AnimalDto;
import com.example.aniamlwaruser.domain.dto.BuildingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DRAW-SERVICE")
public interface DrawClient {
    @GetMapping("/api/animals/{animalId}")
    AnimalDto getAnimalById(@PathVariable("animalId") Long id);

    @GetMapping("/api/buildings/{buildingId}")
    BuildingDto getBuildingById(@PathVariable("buildingId") Long id);
}
