package com.example.aniamlwaruser.controller;

import com.example.aniamlwaruser.config.JwtService;
import com.example.aniamlwaruser.config.TokenInfo;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.request.LoginRequest;
import com.example.aniamlwaruser.domain.request.SignupRequest;
import com.example.aniamlwaruser.domain.response.LoginResponse;
import com.example.aniamlwaruser.domain.response.UserResponse;
import com.example.aniamlwaruser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }

    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequest signupRequest){
        userService.signUp(signupRequest);
    }

    @GetMapping("/me")
    public TokenInfo me(@AuthenticationPrincipal TokenInfo tokenInfo){
        return tokenInfo;
    }

    @GetMapping("/findUser/{id}")
    public UserResponse findByuserId(@PathVariable String id){
        return userService.findUserByUserId(id);
    }

    @GetMapping("/findUser/{userUUID}")
    public UserResponse findByuserUUID(@PathVariable UUID userUUID){
        return userService.findUserByUserUUId(userUUID);
    }

    @PostMapping("/newAccessToken")
    public ResponseEntity<?> newAccessToken(@RequestBody String refreshToken) {
        String newAccessToken = jwtService.generateNewAccessToken(refreshToken);
        return ResponseEntity.ok().body(newAccessToken);
    }

    @PostMapping("/updatePower")
    public ResponseEntity<?> updatePower(@AuthenticationPrincipal TokenInfo tokenInfo) {
        User user = userService.getUserByUserId(tokenInfo.getId());
        userService.updatePowerForUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateLandForm")
    public ResponseEntity<?> updateLandForm(@AuthenticationPrincipal TokenInfo tokenInfo) {
        User user = userService.getUserByUserId(tokenInfo.getId());
        userService.updateLandFormForUser(user);
        return ResponseEntity.ok().build();
    }
}
