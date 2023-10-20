package com.example.aniamlwaruser.repository;



import com.example.aniamlwaruser.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository
    extends JpaRepository<User, UUID> {
    Optional<User> findByid(String id);

    Optional<User> findByUserUUID(UUID userUUID);
}
