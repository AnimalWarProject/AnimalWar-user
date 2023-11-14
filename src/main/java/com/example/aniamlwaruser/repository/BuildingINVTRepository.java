package com.example.aniamlwaruser.repository;


import com.example.aniamlwaruser.domain.entity.*;
import com.example.aniamlwaruser.domain.response.BuildingsResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BuildingINVTRepository extends JpaRepository<UserBuilding,Long> {

    @Query("SELECT b FROM UserBuilding b WHERE b.user.userUUID=:userUUID")
    List<UserBuilding> findByUserUUID(UUID userUUID);

    Optional<UserBuilding> findByUserAndBuilding(User userUUID, Building buildingId);

    @Query("SELECT ub FROM UserBuilding ub WHERE ub.user.userUUID=:userUUID AND ub.building.grade=:grade")
    List<UserBuilding> findAllByGrade(UUID userUUID, Grade grade);
}
