package com.example.aniamlwaruser.domain.response;

import com.example.aniamlwaruser.domain.entity.Species;
import com.example.aniamlwaruser.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
public class UserResponse {
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

//     public UserResponse toKafka(User user){
//        return UserResponse.builder()
//                .id(user.getId())
//                .nickName(user.getNickName())
//                .food(user.getFood())
//                .iron(user.getIron())
//                .wood(user.getWood())
//                .gold(user.getGold())
//                .attackPower(user.getAttackPower())
//                .defensePower(user.getDefensePower())
//                .battlePoint(user.getBattlePoint())
//                .profileImage(user.getProfileImage())
//                .species(user.getSpecies())
//                .build();
//    }
}
