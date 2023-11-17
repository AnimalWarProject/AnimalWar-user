package com.example.aniamlwaruser.controller;

import com.example.aniamlwaruser.config.JwtService;
import com.example.aniamlwaruser.config.TokenInfo;
import com.example.aniamlwaruser.domain.entity.Grade;
import com.example.aniamlwaruser.domain.entity.UserAnimal;
import com.example.aniamlwaruser.domain.entity.UserBuilding;
import com.example.aniamlwaruser.domain.request.INVTRequest;
import com.example.aniamlwaruser.domain.response.AnimalsResponse;
import com.example.aniamlwaruser.domain.response.BuildingsResponse;
import com.example.aniamlwaruser.service.INVTService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/inventory")
public class INVTController {

    private final INVTService INVTService;
    private final JwtService jwtService;

    @GetMapping("/animals") // 동물 인벤토리 불러오기
    public List<AnimalsResponse> getAnimals(@RequestHeader("Authorization") String accessToken){

        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();
        return INVTService.getAnimals(userUUID);
    }

    @GetMapping("/buildings") // 건물 인벤토리 불러오기
    public List<BuildingsResponse> getBuildings(@RequestHeader("Authorization") String accessToken){

        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();
        return INVTService.getBuildings(userUUID);
    }

    @PostMapping("/animals")
    public void insertAnimals(INVTRequest invtRequest){
        INVTService.insertAnimal(invtRequest);
    }

    @PostMapping("/buildings")
    public void insertBuildings(INVTRequest invtRequest){
        INVTService.insertAnimal(invtRequest);
    }

    @GetMapping("/animals/{grade}")
    public List<UserAnimal> findAnimalByGrade(@RequestHeader("Authorization") String accessToken, @RequestParam(name = "grade") String grade) {
        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();
        System.out.println("---------------------------------userUUID : "+ userUUID);
        List<UserAnimal> allByGrade = INVTService.findAllByGrade(userUUID, Grade.valueOf(grade));
        return allByGrade;
    }

    @GetMapping("/buildings/{grade}")
    public List<UserBuilding> findBuildingByGrade(@RequestHeader("Authorization") String accessToken, @RequestParam(name = "grade") String grade) {
        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();
        System.out.println("---------------------------------userUUID : "+ userUUID);
        List<UserBuilding> allByGrade = INVTService.findBuildingByGrade(userUUID, Grade.valueOf(grade));
        System.out.println("---------------------------------grade : "+ grade);
        return allByGrade;
    }


}
