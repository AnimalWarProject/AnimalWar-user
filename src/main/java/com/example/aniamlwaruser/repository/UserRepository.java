package com.example.aniamlwaruser.repository;



import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.response.NickNameResponse2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository
    extends JpaRepository<User, UUID> {
    Optional<User> findByid(String id);

    Optional<User> findByUserUUID(UUID userUUID);


    Optional<User> findByNickName(String nickName);


    @Query("SELECT new com.example.aniamlwaruser.domain.response.NickNameResponse2(u.nickName)" +
            " FROM User u ORDER BY u.battlePoint DESC LIMIT 100")
    List<NickNameResponse2> findTopUserByBattlePoint();

    @Query("SELECT u FROM User u ORDER BY (u.attackPower + u.defensePower) DESC LIMIT 100")
    List<User> findTopUserByPower();

    @Query("SELECT new com.example.aniamlwaruser.domain.response.NickNameResponse2(u.nickName)" +
            " FROM User u ORDER BY u.gold DESC LIMIT 100")
    List<NickNameResponse2> findTopUserByGold();
}
