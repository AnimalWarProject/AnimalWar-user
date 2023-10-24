package com.example.aniamlwaruser.controller;

import com.example.aniamlwaruser.domain.request.DrawRequest;
import com.example.aniamlwaruser.domain.response.DrawResultResponseDto;
import com.example.aniamlwaruser.domain.response.UserResponse;
import com.example.aniamlwaruser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")

public class UserController {
    private final UserService userService;



    @GetMapping("/findByID/{id}")
    public UserResponse findByUserId(@PathVariable String id){
        return userService.findUserByUserId(id);
    }

    @GetMapping("/findByUUID/{userUUID}")
    public UserResponse findByUserUUID(@PathVariable UUID userUUID){
        return userService.findUserByUserUUId(userUUID);
    }

    @PostMapping("/requestTerrain")
    public void requestTerrain(@RequestBody UUID userUUID) {
        userService.requestTerrain(userUUID);
    }

    @PostMapping("/draw") // draw 서비스
    public List<DrawResultResponseDto> requestUser(@RequestBody DrawRequest request) {
        return userService.requestDraw(request);
    }

}
