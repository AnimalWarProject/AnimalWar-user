package com.example.aniamlwaruser.service;

import com.example.aniamlwaruser.common.RestError;
import com.example.aniamlwaruser.common.RestResult;
import com.example.aniamlwaruser.domain.entity.Building;
import com.example.aniamlwaruser.domain.entity.Grade;
import com.example.aniamlwaruser.domain.kafka.MixProducer;
import com.example.aniamlwaruser.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;
    private final MixProducer mixProducer;


    public ResponseEntity<RestResult<Object>> saveBuildings() {

        if(!buildingRepository.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new RestResult<>("error",new RestError("DUPLICATE", "Data already exists")));
        }

        List<Building> buildinglist = buildingRepository.saveAll(
                List.of(
                        Building.builder().name("본부").grade(Grade.NORMAL).attackPower(0).defencePower(0).life(10000).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("일반 동물 훈련소").grade(Grade.NORMAL).attackPower(10).defencePower(10).life(100).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("일반 목공소").grade(Grade.NORMAL).attackPower(10).defencePower(10).life(100).woodRate(50).ironRate(0).foodRate(0).build(),
                        Building.builder().name("일반 제철소").grade(Grade.NORMAL).attackPower(10).defencePower(10).life(100).woodRate(0).ironRate(50).foodRate(0).build(),
                        Building.builder().name("일반 식품저장소").grade(Grade.NORMAL).attackPower(10).defencePower(10).life(100).woodRate(0).ironRate(0).foodRate(50).build(),
                        Building.builder().name("일반 동물원").grade(Grade.NORMAL).attackPower(10).defencePower(10).life(100).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("나무 공격토템").grade(Grade.NORMAL).attackPower(30).defencePower(0).life(0).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("나무 방어토템").grade(Grade.NORMAL).attackPower(0).defencePower(30).life(0).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("니무 체력토템").grade(Grade.NORMAL).attackPower(0).defencePower(0).life(80).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("나무울타리").grade(Grade.NORMAL).attackPower(5).defencePower(5).life(10).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("듬성듬성한 꽃밭").grade(Grade.NORMAL).attackPower(0).defencePower(15).life(0).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("고급 동물 훈련소").grade(Grade.RARE).attackPower(20).defencePower(20).life(200).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("고급 목공소").grade(Grade.RARE).attackPower(20).defencePower(20).life(200).woodRate(70).ironRate(0).foodRate(0).build(),
                        Building.builder().name("고급 제철소").grade(Grade.RARE).attackPower(20).defencePower(20).life(200).woodRate(0).ironRate(70).foodRate(0).build(),
                        Building.builder().name("고급 식품저장소").grade(Grade.RARE).attackPower(20).defencePower(20).life(200).woodRate(0).ironRate(0).foodRate(70).build(),
                        Building.builder().name("고급 동물원").grade(Grade.RARE).attackPower(20).defencePower(20).life(200).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("돌 공격토템").grade(Grade.RARE).attackPower(50).defencePower(0).life(0).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("돌 방어토템").grade(Grade.RARE).attackPower(0).defencePower(50).life(0).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("돌 체력토템").grade(Grade.RARE).attackPower(0).defencePower(0).life(100).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("돌 울타리").grade(Grade.RARE).attackPower(7).defencePower(7).life(15).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("우거진 꽃밭").grade(Grade.RARE).attackPower(0).defencePower(25).life(0).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("희귀 동물 훈련소").grade(Grade.SUPERRARE).attackPower(30).defencePower(30).life(250).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("희귀 목공소").grade(Grade.SUPERRARE).attackPower(30).defencePower(30).life(250).woodRate(80).ironRate(0).foodRate(0).build(),
                        Building.builder().name("희귀 제철소").grade(Grade.SUPERRARE).attackPower(30).defencePower(30).life(250).woodRate(0).ironRate(80).foodRate(0).build(),
                        Building.builder().name("희귀 식품저장소").grade(Grade.SUPERRARE).attackPower(30).defencePower(30).life(250).woodRate(0).ironRate(0).foodRate(80).build(),
                        Building.builder().name("희귀 동물원").grade(Grade.SUPERRARE).attackPower(30).defencePower(30).life(250).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("철 공격토템").grade(Grade.SUPERRARE).attackPower(65).defencePower(0).life(0).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("철 방어토템").grade(Grade.SUPERRARE).attackPower(0).defencePower(65).life(0).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("철 체력토템").grade(Grade.SUPERRARE).attackPower(0).defencePower(0).life(115).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("철 울타리").grade(Grade.SUPERRARE).attackPower(10).defencePower(10).life(25).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("잘 가꾸어진 꽃밭").grade(Grade.SUPERRARE).attackPower(0).defencePower(50).life(0).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("상급 동물 훈련소").grade(Grade.UNIQUE).attackPower(40).defencePower(40).life(300).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("상급 목공소").grade(Grade.UNIQUE).attackPower(40).defencePower(40).life(300).woodRate(90).ironRate(0).foodRate(0).build(),
                        Building.builder().name("상급 제철소").grade(Grade.UNIQUE).attackPower(40).defencePower(40).life(300).woodRate(0).ironRate(90).foodRate(0).build(),
                        Building.builder().name("상급 식품저장소").grade(Grade.UNIQUE).attackPower(40).defencePower(40).life(300).woodRate(0).ironRate(0).foodRate(90).build(),
                        Building.builder().name("상급 동물원").grade(Grade.UNIQUE).attackPower(40).defencePower(40).life(300).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("합금 공격토템").grade(Grade.UNIQUE).attackPower(80).defencePower(0).life(0).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("합금 방어토템").grade(Grade.UNIQUE).attackPower(0).defencePower(80).life(0).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("합금 체력토템").grade(Grade.UNIQUE).attackPower(0).defencePower(0).life(120).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("합금 울타리").grade(Grade.UNIQUE).attackPower(15).defencePower(15).life(40).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("정원").grade(Grade.UNIQUE).attackPower(0).defencePower(80).life(0).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("전설 동물 훈련소").grade(Grade.LEGEND).attackPower(50).defencePower(50).life(400).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("전설 목공소").grade(Grade.LEGEND).attackPower(50).defencePower(50).life(400).woodRate(100).ironRate(0).foodRate(0).build(),
                        Building.builder().name("전설 제철소").grade(Grade.LEGEND).attackPower(50).defencePower(50).life(400).woodRate(0).ironRate(100).foodRate(0).build(),
                        Building.builder().name("전설 식품저장소").grade(Grade.LEGEND).attackPower(50).defencePower(50).life(400).woodRate(0).ironRate(0).foodRate(100).build(),
                        Building.builder().name("전설 동물원").grade(Grade.LEGEND).attackPower(50).defencePower(50).life(400).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("다이아몬드 공격토템").grade(Grade.LEGEND).attackPower(100).defencePower(0).life(0).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("다이아몬드 방어토템").grade(Grade.LEGEND).attackPower(0).defencePower(100).life(0).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("다이아몬드 체력토템").grade(Grade.LEGEND).attackPower(0).defencePower(0).life(200).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("다이아몬드 울타리").grade(Grade.LEGEND).attackPower(20).defencePower(20).life(70).woodRate(0).ironRate(0).foodRate(0).build(),
                        Building.builder().name("식물원").grade(Grade.LEGEND).attackPower(0).defencePower(120).life(0).woodRate(0).ironRate(0).foodRate(0).build()
         ));

//        mixProducer.sendBuilding(buildinglist);
        return  ResponseEntity.ok(new RestResult<>("success","Data saved successfully"));

    }
}