package com.example.aniamlwaruser.controller;

import com.example.aniamlwaruser.domain.dto.TerrainRequestDto;
import com.example.aniamlwaruser.domain.response.UserResponse;
import com.example.aniamlwaruser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")

public class UserController {
    private final UserService userService;
//    private final UserProducer userProducer;



    @GetMapping("/findByID/{id}")
    public UserResponse findByUserId(@PathVariable String id){
        return userService.findUserByUserId(id);
    }

    @GetMapping("/findByUUID/{userUUID}")
    public UserResponse findByUserUUID(@PathVariable UUID userUUID){
        return userService.findUserByUserUUId(userUUID);
    }

    @PostMapping("/requestTerrain")
    public void requestTerrain(@RequestBody TerrainRequestDto terrainRequestDto) {
//        userProducer.requestTerrain(terrainRequestDto);
    }
}
