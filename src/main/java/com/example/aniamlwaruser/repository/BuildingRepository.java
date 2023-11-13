package com.example.aniamlwaruser.repository;

import com.example.aniamlwaruser.domain.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    Optional<Building> findByName(String name);
}