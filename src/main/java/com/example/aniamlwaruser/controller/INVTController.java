package com.example.aniamlwaruser.controller;

import com.example.aniamlwaruser.config.JwtService;
import com.example.aniamlwaruser.config.TokenInfo;
import com.example.aniamlwaruser.domain.entity.Grade;
import com.example.aniamlwaruser.domain.entity.UserAnimal;
import com.example.aniamlwaruser.domain.entity.UserBuilding;
import com.example.aniamlwaruser.domain.request.*;
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
        return INVTService.getAnimal(userUUID, itemId);
    }


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

    @PostMapping("/updatePlace")
    public ResponseEntity<Boolean> updateInventoryItems(@RequestHeader("Authorization") String accessToken, @RequestBody UpdateInventoryRequest updateRequest) {
        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();

        // Update animals
        for (UpdateItem updateItem : updateRequest.getAnimalItems()) {
            boolean updated = INVTService.updatePlacedQuantity(userUUID, updateItem);
            if (!updated) {
                return ResponseEntity.badRequest().body(false);
            }
        }
        // Update buildings
        for (UpdateItem updateItem : updateRequest.getBuildingItems()) {
            boolean updated = INVTService.updatePlacedQuantity(userUUID, updateItem);
            if (!updated) {
                return ResponseEntity.badRequest().body(false);
            }
        }
        System.out.println(updateRequest);
        return ResponseEntity.ok(true);
    }



    @PostMapping("/removePlace")
    public ResponseEntity<Boolean> removePlace(@RequestHeader("Authorization") String accessToken, @RequestBody RemoveInventoryRequest removeRequest) {
        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();

        // 동물 및 건물 아이템 제거 처리
        for (RemoveItem removeItem : removeRequest.getAnimalItems()) {
            boolean updated = INVTService.removePlace(userUUID, removeItem);
            if (!updated) {
                return ResponseEntity.badRequest().body(false);
            }
        }
        for (RemoveItem removeItem : removeRequest.getBuildingItems()) {
            boolean updated = INVTService.removePlace(userUUID, removeItem);
            if (!updated) {
                return ResponseEntity.badRequest().body(false);
            }
        }

        return ResponseEntity.ok(true);
    }

//    @GetMapping("/animals")
//    public ResponseEntity<List<AnimalsResponse>> findAllAnimals(@RequestHeader("Authorization") String accessToken) {
//        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
//        UUID userUUID = tokenInfo.getUserUUID();
//        List<AnimalsResponse> animals = INVTService.findAllAnimals(userUUID);
//        return ResponseEntity.ok(animals);
//    }
//
//    // 건물 데이터 반환
//    @GetMapping("/buildings")
//    public ResponseEntity<List<BuildingsResponse>> findAllBuildings(@RequestHeader("Authorization") String accessToken) {
//        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
//        UUID userUUID = tokenInfo.getUserUUID();
//        List<BuildingsResponse> buildings = INVTService.findAllBuildings(userUUID);
//        return ResponseEntity.ok(buildings);
//    }


    }
