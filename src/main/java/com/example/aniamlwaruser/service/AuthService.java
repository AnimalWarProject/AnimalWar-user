package com.example.aniamlwaruser.service;

import com.example.aniamlwaruser.config.JwtService;
import com.example.aniamlwaruser.config.TokenInfo;
import com.example.aniamlwaruser.domain.dto.TerrainRequestDto;
import com.example.aniamlwaruser.domain.entity.Building;
import com.example.aniamlwaruser.domain.entity.RefreshToken;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.entity.UserBuilding;
import com.example.aniamlwaruser.domain.kafka.FirstTerrainProducer;
import com.example.aniamlwaruser.domain.kafka.MatchProducer;
import com.example.aniamlwaruser.domain.request.LoginRequest;
import com.example.aniamlwaruser.domain.request.SignupRequest;
import com.example.aniamlwaruser.domain.response.LoginResponse;
import com.example.aniamlwaruser.domain.response.UserResponse;
import com.example.aniamlwaruser.exception.InvalidPasswordException;
import com.example.aniamlwaruser.exception.UserNotFoundException;
import com.example.aniamlwaruser.repository.BuildingRepository;
import com.example.aniamlwaruser.repository.RefreshTokenRepository;
import com.example.aniamlwaruser.repository.UserBuildingRepository;
import com.example.aniamlwaruser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MatchProducer matchProducer;
    private final FirstTerrainProducer firstTerrainProducer;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BuildingRepository buildingRepository;
    private final UserBuildingRepository userBuildingRepository;


    @Transactional
    public void signUp(SignupRequest request) {
        // User entity 생성 및 초기 자원 설정
        User user = User.builder()
                .id(request.getId())
                .nickName(request.getNickName())
                .profileImage(request.getProfileImage())
                .password(passwordEncoder.encode(request.getPassword()))
                .species(request.getSpecies())
                .food(50000)
                .wood(50000)
                .iron(50000)
                .gold(20000)
                .totalWoodRate(0)
                .totalIronRate(0)
                .totalFoodRate(0)
                .build();
        userRepository.save(user); // 유저 저장

        // 기본 건물 목록 생성 및 저장
        List<String> defaultBuildingNames = List.of("본부", "일반 동물 훈련소", "일반 목공소", "일반 제철소", "일반 식품 저장소");
        for (String buildingName : defaultBuildingNames) {
            Building building = buildingRepository.findByName(buildingName)
                    .orElseThrow(() -> new RuntimeException("Not found Building with name: " + buildingName));

            UserBuilding userBuilding = UserBuilding.builder()
                    .user(user)
                    .building(building)
                    .ownedQuantity(1)
                    .placedQuantity(0)
                    .upgrade(0)
                    .build();
            userBuildingRepository.save(userBuilding);
        }


        //카프카
        UserResponse build = UserResponse.builder()
                .uuid(user.getUserUUID())
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

        TerrainRequestDto terrainRequestDto = new TerrainRequestDto(user.getUserUUID());
        firstTerrainProducer.sendCreateTerrainRequest(terrainRequestDto);
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


    public String getRefreshToken(String id) {
        return refreshTokenRepository.findById(id)
                .map(RefreshToken::getToken)
                .orElseThrow(() -> new RuntimeException("Refresh Token not found for the given user id."));
    }


    public void logout(TokenInfo tokenInfo) {
        String userId = tokenInfo.getId();
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(userId);
        refreshToken.ifPresent(refreshTokenRepository::delete);
    }
}