package com.example.aniamlwaruser.controller;

import com.example.aniamlwaruser.config.JwtService;
import com.example.aniamlwaruser.config.TokenInfo;
import com.example.aniamlwaruser.domain.request.DeleteAnimalRequest;
import com.example.aniamlwaruser.domain.request.DeleteBuildingRequest;
import com.example.aniamlwaruser.domain.request.INVTRequest;
import com.example.aniamlwaruser.domain.response.AnimalsResponse;
import com.example.aniamlwaruser.domain.response.BuildingsResponse;
import com.example.aniamlwaruser.service.INVTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/myanimal/{itemId}") // 동물 조회
    public AnimalsResponse getAnimals(@RequestHeader("Authorization") String accessToken,
                                      @PathVariable("itemId") Long itemId){
        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();
        System.out.println("동물 조회 : " + itemId);
        return INVTService.getAnimal(userUUID, itemId);
    }

//    @PostMapping("/animals")
//    public void insertAnimals(@RequestBody INVTRequest invtRequest){
//        INVTService.insertAnimal(invtRequest);
//    }
//
//    @PostMapping("/buildings")
//    public void insertBuildings(@RequestBody INVTRequest invtRequest){
//        INVTService.insertBuilding(invtRequest);
//    }

    @PostMapping("/delete/animal")
    public ResponseEntity<Boolean> deleteAnimalItem(@RequestHeader("Authorization") String accessToken, @RequestBody DeleteAnimalRequest request){
        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();
        boolean deleted = INVTService.deleteInvenAnimal(userUUID, request);
        return ResponseEntity.ok(deleted);
    }

    @PostMapping("/delete/building")
    public ResponseEntity<Boolean> deleteBuildingItem(@RequestHeader("Authorization") String accessToken, @RequestBody DeleteBuildingRequest request){
        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();
        boolean deleted = INVTService.deleteInvenBuilding(userUUID, request);
        return ResponseEntity.ok(deleted);
    }

}
