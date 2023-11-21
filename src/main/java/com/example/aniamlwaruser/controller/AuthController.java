package com.example.aniamlwaruser.controller;

import com.example.aniamlwaruser.config.JwtService;
import com.example.aniamlwaruser.config.TokenInfo;
import com.example.aniamlwaruser.domain.request.LoginRequest;
import com.example.aniamlwaruser.domain.request.SignupRequest;
import com.example.aniamlwaruser.domain.response.LoginResponse;
import com.example.aniamlwaruser.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        System.out.println("로그인");
        return authService.login(loginRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal TokenInfo tokenInfo) {
        authService.logout(tokenInfo);
        return ResponseEntity.ok().body("User logged out successfully");
    }


    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequest signupRequest){
        authService.signUp(signupRequest);
    }

    @GetMapping("/me")
    public TokenInfo me(@AuthenticationPrincipal TokenInfo tokenInfo){
        return tokenInfo;
    }

    @PostMapping("/newAsccessToken")
    public ResponseEntity<?> newAccessToken(@RequestBody String refreshToken) {
        String newAccessToken = jwtService.generateNewAccessToken(refreshToken);
        return ResponseEntity.ok().body(newAccessToken);
    }

    @GetMapping("/getRefreshToken")
    public ResponseEntity<String> getRefreshTokenForUser(@RequestParam String id) {
        return ResponseEntity.ok().body(authService.getRefreshToken(id));
    }
}
