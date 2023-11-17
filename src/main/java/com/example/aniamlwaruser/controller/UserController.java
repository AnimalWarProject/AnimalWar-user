package com.example.aniamlwaruser.controller;

import com.example.aniamlwaruser.config.JwtService;
import com.example.aniamlwaruser.config.TokenInfo;
import com.example.aniamlwaruser.domain.request.DrawRequest;
import com.example.aniamlwaruser.domain.request.UserUpdateRequest;
import com.example.aniamlwaruser.domain.response.ReTerrainResponse;
import com.example.aniamlwaruser.domain.response.UserResponse;
import com.example.aniamlwaruser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")

public class UserController {
    private final UserService userService;
    private final JwtService jwtService;


    @GetMapping("")
    public UserResponse findByToken(@AuthenticationPrincipal TokenInfo tokenInfo){
        return userService.findUserByUserUUId(tokenInfo.getUserUUID());
    }

    @GetMapping("/findByID/{id}")
    public UserResponse findByUserId(@PathVariable String id){
        return userService.findUserByUserId(id);
    }

    @GetMapping("/findByUUID/{userUUID}")
    public UserResponse findByUserUUID(@PathVariable UUID userUUID){
        return userService.findUserByUserUUId(userUUID);
    }

    @GetMapping("/findByNickName/{nickName}")
    public UserResponse findByNickName(@PathVariable String nickName){
        return userService.findUserByNickName(nickName);
    }



    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestHeader String accessToken,
                                             @RequestBody UserUpdateRequest request) {
        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();

        userService.updateUser(userUUID, request);
        return ResponseEntity.ok("유저 정보 변경 완료");
    }



    @PostMapping("/terrain")
    public ReTerrainResponse requestTerrain(@AuthenticationPrincipal TokenInfo tokenInfo) {
//        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();

        return userService.requestTerrain(userUUID);
    }

    @PostMapping("/draw") // draw 서비스 돈 차감
    public void requestUser(@RequestBody DrawRequest request) {
        userService.requestDraw(request);
    }

}
