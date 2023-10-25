package com.example.aniamlwaruser.repository;



import com.example.aniamlwaruser.domain.entity.User;
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


    @Query("SELECT u.nickName FROM User as u ORDER BY u.battlePoint DESC LIMIT 100")
    List<User> findTopUserByBattlePoint();

    @Query("SELECT u.nickName FROM User as u ORDER BY (u.attackPower + u.defensePower) DESC LIMIT 100")
    List<User> findTopUserByPower();

    @Query("SELECT u.nickName FROM User as u ORDER BY u.gold DESC LIMIT 100")
    List<User> findTopUserByGold();
}
