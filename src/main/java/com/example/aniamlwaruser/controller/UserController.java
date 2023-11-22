package com.example.aniamlwaruser.controller;

import com.example.aniamlwaruser.config.JwtService;
import com.example.aniamlwaruser.config.TokenInfo;
import com.example.aniamlwaruser.domain.request.*;
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
        UUID userUUID = tokenInfo.getUserUUID();
        return userService.requestTerrain(userUUID);
    }

    @PostMapping("/draw") // draw 서비스 돈 차감
    public void requestUser(@RequestHeader("Authorization") String accessToken, @RequestBody DrawRequest request) {
        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();
        userService.requestDraw(userUUID, request.count());
    }

    @PostMapping("/price") // market에서 팔린금액 수령
    public void takePrice(@RequestHeader("Authorization") String accessToken, @RequestBody PriceRequest request) {
        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();
        userService.takePrice(userUUID, request);
    }

    @PostMapping("/buyitem") // market구매
    public void buyItem(@RequestHeader("Authorization") String accessToken, @RequestBody BuyItemRequest request) {
        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();
        System.out.println("buyAnimal : " + request.getUserId());
        userService.buyAnimal(userUUID, request);
    }

    @PostMapping("/cancelitem") // market취소
    public void cancleItem(@RequestHeader("Authorization") String accessToken, @RequestBody CancelItemRequest request) {
        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();
        userService.cancelItem(userUUID, request);
    }

    @PostMapping("/upgrade") // 강화서비스
    public void upGrade(@RequestHeader("Authorization") String accessToken, @RequestBody UpgradeRequest request) {
        TokenInfo tokenInfo = jwtService.parseAccessToken(accessToken.replace("Bearer ", ""));
        UUID userUUID = tokenInfo.getUserUUID();
        userService.upGrade(userUUID, request);
    }

}
