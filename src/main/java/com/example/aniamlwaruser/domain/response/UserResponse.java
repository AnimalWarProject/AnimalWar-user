package com.example.aniamlwaruser.domain.response;

import com.example.aniamlwaruser.domain.entity.Species;
import com.example.aniamlwaruser.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Builder
public class UserResponse {

    private UUID uuid;
    private String id;
    private String nickName;
    private int food;
    private int iron;
    private int wood;
    private int gold;

    private int attackPower;
    private int defensePower;
    private int battlePoint;

    private String profileImage;

    private Species species;

    public static UserResponse userResponseBuild(User user) {
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
}
