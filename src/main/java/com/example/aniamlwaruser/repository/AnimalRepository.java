package com.example.aniamlwaruser.repository;

import com.example.aniamlwaruser.domain.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal,Long> {
}
