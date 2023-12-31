package com.example.aniamlwaruser.service;


import com.example.aniamlwaruser.domain.entity.Building;
import com.example.aniamlwaruser.domain.entity.BuildingType;
import com.example.aniamlwaruser.domain.entity.Grade;
import com.example.aniamlwaruser.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;

    @Bean
    public ResponseEntity<String> saveBuildings() {
        // buildingRepository 비어 있으면 저장
        if(!buildingRepository.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Data already exists");
        }

        buildingRepository.saveAll(List.of(
                Building.builder().name("본부").grade(Grade.NORMAL).attackPower(0).defencePower(0).life(10000).woodRate(0).ironRate(0).foodRate(0).imagePath("CommandCenter.webp").buildingType(BuildingType.COMMANDCENTER).build(),
                Building.builder().name("일반 동물 훈련소").grade(Grade.NORMAL).attackPower(10).defencePower(10).life(100).woodRate(0).ironRate(0).foodRate(0).imagePath("NormalTrainingCenter.webp").buildingType(BuildingType.TRANINGCENTER).build(),
                Building.builder().name("일반 목공소").grade(Grade.NORMAL).attackPower(10).defencePower(10).life(100).woodRate(50).ironRate(0).foodRate(0).imagePath("NormalWoodFactory.webp").buildingType(BuildingType.WOODFACTORY).build(),
                Building.builder().name("일반 제철소").grade(Grade.NORMAL).attackPower(10).defencePower(10).life(100).woodRate(0).ironRate(50).foodRate(0).imagePath("NormalSteelMill.webp").buildingType(BuildingType.STEELMILL).build(),
                Building.builder().name("일반 식품 저장소").grade(Grade.NORMAL).attackPower(10).defencePower(10).life(100).woodRate(0).ironRate(0).foodRate(50).imagePath("NormalFoodTower.webp").buildingType(BuildingType.FOODSTORAGE).build(),
                Building.builder().name("일반 동물원").grade(Grade.NORMAL).attackPower(10).defencePower(10).life(100).woodRate(0).ironRate(0).foodRate(0).imagePath("NormalZoo.webp").buildingType(BuildingType.ZOO).build(),
                Building.builder().name("나무 공격 토템").grade(Grade.NORMAL).attackPower(30).defencePower(0).life(0).woodRate(0).ironRate(0).foodRate(0).imagePath("WoodAttackTotem.webp").buildingType(BuildingType.TOTEM).build(),
                Building.builder().name("나무 방어 토템").grade(Grade.NORMAL).attackPower(0).defencePower(30).life(0).woodRate(0).ironRate(0).foodRate(0).imagePath("WoodDefenseTotem.webp").buildingType(BuildingType.TOTEM).build(),
                Building.builder().name("나무 체력 토템").grade(Grade.NORMAL).attackPower(0).defencePower(0).life(80).woodRate(0).ironRate(0).foodRate(0).imagePath("WoodLifeTotem.webp").buildingType(BuildingType.TOTEM).build(),
                Building.builder().name("나무 울타리").grade(Grade.NORMAL).attackPower(5).defencePower(5).life(10).woodRate(0).ironRate(0).foodRate(0).imagePath("WoodFence.webp").buildingType(BuildingType.FENCE).build(),
                Building.builder().name("듬성듬성한 꽃밭").grade(Grade.NORMAL).attackPower(0).defencePower(15).life(0).woodRate(0).ironRate(0).foodRate(0).imagePath("NormalFlower.webp").buildingType(BuildingType.FLOWER).build(),
                Building.builder().name("고급 동물 훈련소").grade(Grade.RARE).attackPower(20).defencePower(20).life(200).woodRate(0).ironRate(0).foodRate(0).imagePath("RareTrainingCenter.webp").buildingType(BuildingType.TRANINGCENTER).build(),
                Building.builder().name("고급 목공소").grade(Grade.RARE).attackPower(20).defencePower(20).life(200).woodRate(70).ironRate(0).foodRate(0).imagePath("RareWoodFactory.webp").buildingType(BuildingType.WOODFACTORY).build(),
                Building.builder().name("고급 제철소").grade(Grade.RARE).attackPower(20).defencePower(20).life(200).woodRate(0).ironRate(70).foodRate(0).imagePath("RareSteelMill.webp").buildingType(BuildingType.STEELMILL).build(),
                Building.builder().name("고급 식품 저장소").grade(Grade.RARE).attackPower(20).defencePower(20).life(200).woodRate(0).ironRate(0).foodRate(70).imagePath("RareFoodStorage.webp").buildingType(BuildingType.FOODSTORAGE).build(),
                Building.builder().name("고급 동물원").grade(Grade.RARE).attackPower(20).defencePower(20).life(200).woodRate(0).ironRate(0).foodRate(0).imagePath("RareZoo.webp").buildingType(BuildingType.ZOO).build(),
                Building.builder().name("돌 공격 토템").grade(Grade.RARE).attackPower(50).defencePower(0).life(0).woodRate(0).ironRate(0).foodRate(0).imagePath("StoneAttackTotem.webp").buildingType(BuildingType.TOTEM).build(),
                Building.builder().name("돌 방어 토템").grade(Grade.RARE).attackPower(0).defencePower(50).life(0).woodRate(0).ironRate(0).foodRate(0).imagePath("StoneDefenseTotem.webp").buildingType(BuildingType.TOTEM).build(),
                Building.builder().name("돌 체력 토템").grade(Grade.RARE).attackPower(0).defencePower(0).life(100).woodRate(0).ironRate(0).foodRate(0).imagePath("StoneLifeTotem.webp").buildingType(BuildingType.TOTEM).build(),
                Building.builder().name("돌 울타리").grade(Grade.RARE).attackPower(7).defencePower(7).life(15).woodRate(0).ironRate(0).foodRate(0).imagePath("StoneFence.webp").buildingType(BuildingType.FENCE).build(),
                Building.builder().name("우거진 꽃밭").grade(Grade.RARE).attackPower(0).defencePower(25).life(0).woodRate(0).ironRate(0).foodRate(0).imagePath("RareFlower.webp").buildingType(BuildingType.FLOWER).build(),
                Building.builder().name("희귀 동물 훈련소").grade(Grade.SUPERRARE).attackPower(30).defencePower(30).life(250).woodRate(0).ironRate(0).foodRate(0).imagePath("SuperRareTrainingCenter.webp").buildingType(BuildingType.TRANINGCENTER).build(),
                Building.builder().name("희귀 목공소").grade(Grade.SUPERRARE).attackPower(30).defencePower(30).life(250).woodRate(80).ironRate(0).foodRate(0).imagePath("SuperRareWoodFactory.webp").buildingType(BuildingType.WOODFACTORY).build(),
                Building.builder().name("희귀 제철소").grade(Grade.SUPERRARE).attackPower(30).defencePower(30).life(250).woodRate(0).ironRate(80).foodRate(0).imagePath("SuperRareSteelMill.webp").buildingType(BuildingType.STEELMILL).build(),
                Building.builder().name("희귀 식품 저장소").grade(Grade.SUPERRARE).attackPower(30).defencePower(30).life(250).woodRate(0).ironRate(0).foodRate(80).imagePath("SuperRareFoodStorage.webp").buildingType(BuildingType.FOODSTORAGE).build(),
                Building.builder().name("희귀 동물원").grade(Grade.SUPERRARE).attackPower(30).defencePower(30).life(250).woodRate(0).ironRate(0).foodRate(0).imagePath("SuperRareZoo.webp").buildingType(BuildingType.ZOO).build(),
                Building.builder().name("철 공격 토템").grade(Grade.SUPERRARE).attackPower(65).defencePower(0).life(0).woodRate(0).ironRate(0).foodRate(0).imagePath("SteelAttackTotem.webp").buildingType(BuildingType.TOTEM).build(),
                Building.builder().name("철 방어 토템").grade(Grade.SUPERRARE).attackPower(0).defencePower(65).life(0).woodRate(0).ironRate(0).foodRate(0).imagePath("SteelDefenseTotem.webp").buildingType(BuildingType.TOTEM).build(),
                Building.builder().name("철 체력 토템").grade(Grade.SUPERRARE).attackPower(0).defencePower(0).life(115).woodRate(0).ironRate(0).foodRate(0).imagePath("SteelLifeTotem.webp").buildingType(BuildingType.TOTEM).build(),
                Building.builder().name("철 울타리").grade(Grade.SUPERRARE).attackPower(10).defencePower(10).life(25).woodRate(0).ironRate(0).foodRate(0).imagePath("SteelFence.webp").buildingType(BuildingType.FENCE).build(),
                Building.builder().name("잘 가꾸어진 꽃밭").grade(Grade.SUPERRARE).attackPower(0).defencePower(50).life(0).woodRate(0).ironRate(0).foodRate(0).imagePath("SuperRareFlower.webp").buildingType(BuildingType.FLOWER).build(),
                Building.builder().name("상급 동물 훈련소").grade(Grade.UNIQUE).attackPower(40).defencePower(40).life(300).woodRate(0).ironRate(0).foodRate(0).imagePath("UniqueTrainingCenter.webp").buildingType(BuildingType.TRANINGCENTER).build(),
                Building.builder().name("상급 목공소").grade(Grade.UNIQUE).attackPower(40).defencePower(40).life(300).woodRate(90).ironRate(0).foodRate(0).imagePath("UniqueWoodFactory.webp").buildingType(BuildingType.WOODFACTORY).build(),
                Building.builder().name("상급 제철소").grade(Grade.UNIQUE).attackPower(40).defencePower(40).life(300).woodRate(0).ironRate(90).foodRate(0).imagePath("UniqueSteelMill.webp").buildingType(BuildingType.STEELMILL).build(),
                Building.builder().name("상급 식품 저장소").grade(Grade.UNIQUE).attackPower(40).defencePower(40).life(300).woodRate(0).ironRate(0).foodRate(90).imagePath("UniqueFoodStorage.webp").buildingType(BuildingType.FOODSTORAGE).build(),
                Building.builder().name("상급 동물원").grade(Grade.UNIQUE).attackPower(40).defencePower(40).life(300).woodRate(0).ironRate(0).foodRate(0).imagePath("UniqueZoo.webp").buildingType(BuildingType.ZOO).build(),
                Building.builder().name("합금 공격 토템").grade(Grade.UNIQUE).attackPower(80).defencePower(0).life(0).woodRate(0).ironRate(0).foodRate(0).imagePath("AlloyAttackTotem.webp").buildingType(BuildingType.TOTEM).build(),
                Building.builder().name("합금 방어 토템").grade(Grade.UNIQUE).attackPower(0).defencePower(80).life(0).woodRate(0).ironRate(0).foodRate(0).imagePath("AlloyDefenseTotem.webp").buildingType(BuildingType.TOTEM).build(),
                Building.builder().name("합금 체력 토템").grade(Grade.UNIQUE).attackPower(0).defencePower(0).life(120).woodRate(0).ironRate(0).foodRate(0).imagePath("AlloyLifeTotem.webp").buildingType(BuildingType.TOTEM).build(),
                Building.builder().name("합금 울타리").grade(Grade.UNIQUE).attackPower(15).defencePower(15).life(40).woodRate(0).ironRate(0).foodRate(0).imagePath("AlloyFence.webp").buildingType(BuildingType.FENCE).build(),
                Building.builder().name("정원").grade(Grade.UNIQUE).attackPower(0).defencePower(80).life(0).woodRate(0).ironRate(0).foodRate(0).imagePath("UniqueFlower.webp").buildingType(BuildingType.FLOWER).build(),
                Building.builder().name("전설 동물 훈련소").grade(Grade.LEGEND).attackPower(50).defencePower(50).life(400).woodRate(0).ironRate(0).foodRate(0).imagePath("LegendTrainingCenter.webp").buildingType(BuildingType.TRANINGCENTER).build(),
                Building.builder().name("전설 목공소").grade(Grade.LEGEND).attackPower(50).defencePower(50).life(400).woodRate(100).ironRate(0).foodRate(0).imagePath("LegendWoodFactory.webp").buildingType(BuildingType.WOODFACTORY).build(),
                Building.builder().name("전설 제철소").grade(Grade.LEGEND).attackPower(50).defencePower(50).life(400).woodRate(0).ironRate(100).foodRate(0).imagePath("LegendSteelMill.webp").buildingType(BuildingType.STEELMILL).build(),
                Building.builder().name("전설 식품 저장소").grade(Grade.LEGEND).attackPower(50).defencePower(50).life(400).woodRate(0).ironRate(0).foodRate(100).imagePath("LegendFoodStorage.webp").buildingType(BuildingType.FOODSTORAGE).build(),
                Building.builder().name("전설 동물원").grade(Grade.LEGEND).attackPower(50).defencePower(50).life(400).woodRate(0).ironRate(0).foodRate(0).imagePath("LegendZoo.webp").buildingType(BuildingType.ZOO).build(),
                Building.builder().name("다이아몬드 공격 토템").grade(Grade.LEGEND).attackPower(100).defencePower(0).life(0).woodRate(0).ironRate(0).foodRate(0).imagePath("DiamondAttackTotem.webp").buildingType(BuildingType.TOTEM).build(),
                Building.builder().name("다이아몬드 방어 토템").grade(Grade.LEGEND).attackPower(0).defencePower(100).life(0).woodRate(0).ironRate(0).foodRate(0).imagePath("DiamondDefenseTotem.webp").buildingType(BuildingType.TOTEM).build(),
                Building.builder().name("다이아몬드 체력 토템").grade(Grade.LEGEND).attackPower(0).defencePower(0).life(200).woodRate(0).ironRate(0).foodRate(0).imagePath("DiamondLifeTotem.webp").buildingType(BuildingType.TOTEM).build(),
                Building.builder().name("다이아몬드 울타리").grade(Grade.LEGEND).attackPower(20).defencePower(20).life(70).woodRate(0).ironRate(0).foodRate(0).imagePath("DiamondFence.webp").buildingType(BuildingType.FENCE).build(),
                Building.builder().name("식물원").grade(Grade.LEGEND).attackPower(0).defencePower(120).life(0).woodRate(0).ironRate(0).foodRate(0).imagePath("LegendFlower.webp").buildingType(BuildingType.FLOWER).build()
        ));

        return ResponseEntity.status(HttpStatus.OK)
            .body("Data saved successfully");
}}
