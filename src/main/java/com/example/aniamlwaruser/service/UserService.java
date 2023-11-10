package com.example.aniamlwaruser.service;


import com.example.aniamlwaruser.domain.dto.TerrainRequestDto;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.response.UserResponse;
import com.example.aniamlwaruser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    // 아이디로 회원 정보 조회
    public UserResponse findUserByUserId(String id) {
        User user = userRepository.findByid(id)
                .orElseThrow(() -> new IllegalArgumentException("USER NOT FOUND FOR USERID: " + id));

        return UserResponse.builder()
                .id(user.getId())
                .nickName(user.getNickName())
                .food(user.getFood())
                .iron(user.getIron())
                .wood(user.getWood())
                .gold(user.getGold())
                .attackPower(user.getAttackPower())
                .defensePower(user.getDefensePower())
                .battlePoint(user.getBattlePoint())
                .profileImage(user.getProfileImage())
                .species(user.getSpecies())
                .build();
    }


    public UserResponse findUserByUserUUId(UUID userUUID) {
        User user = userRepository.findByUserUUID(userUUID)
                .orElseThrow(() -> new IllegalArgumentException("USER NOT FOUND FOR USERID: " + userUUID));

        return UserResponse.builder()
                .id(user.getId())
                .nickName(user.getNickName())
                .food(user.getFood())
                .iron(user.getIron())
                .wood(user.getWood())
                .gold(user.getGold())
                .attackPower(user.getAttackPower())
                .defensePower(user.getDefensePower())
                .battlePoint(user.getBattlePoint())
                .profileImage(user.getProfileImage())
                .species(user.getSpecies())
                .build();
    }

    public void updateUserTerrain(TerrainRequestDto terrainRequestDto) {
        Optional<User> optionalUser = userRepository.findByUserUUID(terrainRequestDto.getUserUUID());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setLand(terrainRequestDto.getLand());
            user.setSea(terrainRequestDto.getSea());
            user.setMountain(terrainRequestDto.getMountain());
            user.setLandForm(terrainRequestDto.getLandForm());

            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found for UUID: " + terrainRequestDto.getUserUUID());
        }
    }

}
