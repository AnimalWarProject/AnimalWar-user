package com.example.aniamlwaruser.repository;

import com.example.aniamlwaruser.domain.entity.UserBuilding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingINVTRepository extends JpaRepository<UserBuilding,Long> {
}
