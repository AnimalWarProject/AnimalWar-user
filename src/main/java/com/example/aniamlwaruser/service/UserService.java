package com.example.aniamlwaruser.service;

import com.example.aniamlwaruser.client.api.DrawClient;
import com.example.aniamlwaruser.client.api.MapClient;
import com.example.aniamlwaruser.config.JwtService;
import com.example.aniamlwaruser.domain.dto.AnimalDto;
import com.example.aniamlwaruser.domain.dto.BuildingDto;
import com.example.aniamlwaruser.domain.dto.LandFormDto;
import com.example.aniamlwaruser.domain.entity.RefreshToken;
import com.example.aniamlwaruser.domain.entity.User;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final DrawClient drawClient;
    private final MapClient mapClient;


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


    public int getTotalAttackPower(User user) {
        int totalAttack = user.getAttackPower();

        for (Long animalId : user.getOwnedAnimalIds()) {
            AnimalDto animal = drawClient.getAnimalById(animalId);
            totalAttack += animal.getAttackPower();
        }

        for (Long buildingId : user.getOwnedBuildingIds()) {
            BuildingDto building = drawClient.getBuildingById(buildingId);
            totalAttack += building.getAttackPower();
        }

        return totalAttack;
    }

    public int getTotalDefensePower(User user) {
        int totalDefense = user.getDefensePower();

        for (Long animalId : user.getOwnedAnimalIds()) {
            AnimalDto animal = drawClient.getAnimalById(animalId);
            totalDefense += animal.getDefensePower();
        }

        for (Long buildingId : user.getOwnedBuildingIds()) {
            BuildingDto building = drawClient.getBuildingById(buildingId);
            totalDefense += building.getDefensePower();
        }

        return totalDefense;
    }


    public User getUserByUserId(String id) {
        return userRepository.findByid(id)
                .orElseThrow(() -> new IllegalArgumentException("USER NOT FOUND FOR USERID: " + id));
    }

    public void updatePowerForUser(User user) {
        int totalAttack = getTotalAttackPower(user);
        int totalDefense = getTotalDefensePower(user);
        user.setAttackPower(totalAttack);
        user.setDefensePower(totalDefense);
        userRepository.save(user);
    }

    public void updateLandFormForUser(User user) {
        LandFormDto landFormDto = mapClient.getLandFormByUserId(user.getId()).getBody();
        user.setLandForm(landFormDto.getLandForm());
        userRepository.save(user);
    }
}
