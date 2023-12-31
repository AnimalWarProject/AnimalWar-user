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
    List<UserBuilding> findUserBuildingByUserUUID(UUID userUUID);

    Optional<UserBuilding> findByUserAndBuilding(User userUUID, Building buildingId);

    @Query("SELECT B FROM UserBuilding B WHERE B.user.userUUID=:userUUID AND B.building.buildingId=:itemId")
    Optional<UserBuilding> findByUserAndBuildingId(UUID userUUID, Long itemId);

    @Query("SELECT B FROm UserBuilding B WHERE B.user.userUUID=:userUUID AND B.building.name=:name AND B.building.buildingType=:Type")
    Optional<UserBuilding> findByUserUUIDAndBuildingNameAndType(UUID userUUID, String name, String Type);

    @Query("SELECT ub FROM UserBuilding ub WHERE ub.user.userUUID=:userUUID AND ub.building.grade=:grade")
    List<UserBuilding> findAllByGrade(UUID userUUID, Grade grade);

    @Query("SELECT ua FROM UserBuilding ua JOIN Building as a on a.buildingId = ua.building.buildingId WHERE ua.user.userUUID=:userUUID AND ua.building.grade = :grade")
    List<UserBuilding> findBuildingByGrade(UUID userUUID, Grade grade);


}
