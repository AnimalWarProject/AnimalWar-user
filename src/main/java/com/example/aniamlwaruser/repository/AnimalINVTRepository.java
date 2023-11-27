package com.example.aniamlwaruser.repository;

import com.example.aniamlwaruser.domain.entity.Animal;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.entity.UserAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnimalINVTRepository extends JpaRepository<UserAnimal,Long> {

    Optional<UserAnimal> findByUserAndAnimal(User userUUID, Animal animalId);


    @Query("SELECT A FROM UserAnimal A WHERE A.user.userUUID=:userUUID AND A.animal.animalId=:itemId")
    Optional<UserAnimal> findByUserAndAnimalId(UUID userUUID, Long itemId);

    @Query("SELECT A FROM UserAnimal A WHERE A.user.userUUID=:userUUID AND A.animal.name=:name AND A.upgrade=:buff")
    Optional<UserAnimal> findByUserUUIDAndAnimalNameAndBuff(UUID userUUID, String name, int buff);


    @Query("SELECT a FROM UserAnimal a WHERE a.user.userUUID=:userUUID")
    List<UserAnimal> findUserAnimalByUserUUID(UUID userUUID);

    @Query("SELECT ua FROM UserAnimal ua WHERE ua.user.userUUID = :byUserUUID AND ua.animal.animalId = :animal")
    UserAnimal findByInven(UUID byUserUUID, Long animal);

    @Query("SELECT ua FROM UserAnimal ua WHERE ua.user.userUUID = :byUserUUID AND ua.animal.animalId = :animal AND ua.upgrade=:buff")
    UserAnimal findByInvenAndBuff(UUID byUserUUID, Long animal, Integer buff);

    @Query("SELECT ua FROM UserAnimal ua WHERE ua.user.userUUID = :byUserUUID AND ua.animal.animalId = :animal AND ua.upgrade=:buff")
    Optional<UserAnimal> findByUserUUIDAndAnimal(UUID byUserUUID, Long animal, Integer buff);

    @Modifying
    @Query("DELETE FROM UserAnimal ua WHERE ua.user.userUUID = :byUserUUID AND ua.animal.animalId = :animal")
    void deleteFindByInven(UUID byUserUUID, Long animal);
}
