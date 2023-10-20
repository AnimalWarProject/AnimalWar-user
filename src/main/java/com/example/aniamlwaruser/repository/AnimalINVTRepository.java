package com.example.aniamlwaruser.repository;

import com.example.aniamlwaruser.domain.entity.UserAnimal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalINVTRepository extends JpaRepository<UserAnimal,Long> {
}
