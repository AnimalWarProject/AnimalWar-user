package com.example.aniamlwaruser.repository;

import com.example.aniamlwaruser.domain.entity.UserAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnimalRepository extends JpaRepository<UserAnimal, Long> {
}
