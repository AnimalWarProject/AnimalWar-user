package com.example.aniamlwaruser.repository;

import com.example.aniamlwaruser.domain.entity.Grade;
import com.example.aniamlwaruser.domain.entity.UserBuilding;
import com.example.aniamlwaruser.domain.response.GetAllResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BuildingINVTRepository extends JpaRepository<UserBuilding,Long> {

    @Query("SELECT b FROM UserBuilding b WHERE b.user.userUUID=:userUUID")
    List<GetAllResponse> findByUserUUID(UUID userUUID);

    @Query("SELECT ub FROM UserBuilding ub WHERE ub.user.userUUID=:userUUID AND ub.building.grade=:grade")
    List<UserBuilding> findAllByGrade(UUID userUUID, Grade grade);
}
