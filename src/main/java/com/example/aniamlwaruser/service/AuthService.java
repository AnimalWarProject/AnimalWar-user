package com.example.aniamlwaruser.service;

import com.example.aniamlwaruser.config.JwtService;
import com.example.aniamlwaruser.domain.entity.RefreshToken;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.kafka.MatchProducer;
import com.example.aniamlwaruser.domain.request.LoginRequest;
import com.example.aniamlwaruser.domain.request.SignupRequest;
import com.example.aniamlwaruser.domain.response.LoginResponse;
import com.example.aniamlwaruser.domain.response.UserResponse;
import com.example.aniamlwaruser.exception.InvalidPasswordException;
import com.example.aniamlwaruser.exception.UserNotFoundException;
import com.example.aniamlwaruser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MatchProducer matchProducer;
    @Transactional
    public void signUp(SignupRequest request) {
        User user = User.builder()
                .id(request.getId())
                .nickName(request.getNickName())
                .profileImage(request.getProfileImage())
                .password(passwordEncoder.encode(request.getPassword()))
                .species(request.getSpecies())
                .build();
        userRepository.save(user);


        User user1 = userRepository.findByid(user.getId()).get();

        UserResponse build = UserResponse.builder()
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
        matchProducer.send(build);
    }


    // 로그인시 액세스토큰과 리프레쉬토큰 생성
    public LoginResponse login(LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByid(request.getId());
        User user = optionalUser.orElseThrow(
                () -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        String accessToken = jwtService.makeAccessToken(user);
        String refreshTokenString = jwtService.makeRefreshToken(user);

        // 리프레시 토큰 저장
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId((user.getId()));
        refreshToken.setToken(refreshTokenString);
        jwtService.saveRefreshToken(user, refreshTokenString);

        return new LoginResponse(accessToken, refreshTokenString);
    }

}
