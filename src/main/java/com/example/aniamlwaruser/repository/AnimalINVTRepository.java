package com.example.aniamlwaruser.repository;

import com.example.aniamlwaruser.domain.entity.Animal;
import com.example.aniamlwaruser.domain.entity.Grade;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.entity.UserAnimal;
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

    @Query("SELECT ua FROM UserAnimal ua WHERE ua.user.userUUID=:userUUID AND ua.animal.grade = :grade")
    List<UserAnimal> findAllByGrade(UUID userUUID, Grade grade);
}
