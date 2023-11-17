package com.example.aniamlwaruser.repository;

import com.example.aniamlwaruser.domain.entity.*;
import com.example.aniamlwaruser.domain.response.AnimalsResponse;
import com.example.aniamlwaruser.domain.response.BuildingsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnimalINVTRepository extends JpaRepository<UserAnimal,Long> {

    Optional<UserAnimal> findByUserAndAnimal(User userUUID, Animal animalId);

    @Query("SELECT a FROM UserAnimal a WHERE a.user.userUUID=:userUUID")
    List<UserAnimal> findUserAnimalByUserUUID(UUID userUUID);

    @Query("SELECT ua FROM UserAnimal ua WHERE ua.user.userUUID = :byUserUUID AND ua.animal.animalId = :animal")
    Optional<UserAnimal> findByInven(UUID byUserUUID, Long animal);

    @Modifying
    @Query("DELETE FROM UserAnimal ua WHERE ua.user.userUUID = :byUserUUID AND ua.animal.animalId = :animal")
    void deleteFindByInven(UUID byUserUUID, Long animal);

    @Query("SELECT ua FROM UserAnimal ua JOIN Animal as a on a.animalId = ua.animal.animalId WHERE ua.user.userUUID=:userUUID AND ua.animal.grade = :grade")
    List<UserAnimal> findAnimalByGrade(UUID userUUID, Grade grade);

    @Query("SELECT ua FROM UserBuilding ua JOIN Building as a on a.buildingId = ua.building.buildingId WHERE ua.user.userUUID=:userUUID AND ua.building.grade = :grade")
    List<UserBuilding> findBuildingByGrade(UUID userUUID, Grade grade);
}
