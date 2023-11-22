package com.example.aniamlwaruser.repository;

import com.example.aniamlwaruser.domain.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserBuildingRepository extends JpaRepository<UserBuilding, Long> {

    Optional<UserBuilding> findByUserAndBuilding(User user, Building building);

    Optional<UserBuilding> findByBuilding_BuildingId(Long buildingId);

}
