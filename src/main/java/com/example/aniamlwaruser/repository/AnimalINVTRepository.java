package com.example.aniamlwaruser.repository;

import com.example.aniamlwaruser.domain.entity.Animal;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.entity.UserAnimal;
import com.example.aniamlwaruser.domain.response.GetAllResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnimalINVTRepository extends JpaRepository<UserAnimal,Long> {

    Optional<UserAnimal> findByUserAndAnimal(User byUserUUID, Animal animal);

    //    @Query("SELECT ua.upgrade FROM UserAnimal ua INNER JOIN User u ON u.id =:byUserUUID INNER JOIN Animal a ON a.animalId = :animal")
//    Optional<UserAnimal> findByInven(UUID byUserUUID, Long animal);

    @Query("SELECT a FROM UserAnimal a WHERE a.user.userUUID=:userUUID")
    List<GetAllResponse> findByUserUUID(UUID userUUID);

    @Query("SELECT ua FROM UserAnimal ua WHERE ua.user.userUUID = :byUserUUID AND ua.animal.animalId = :animal")
    Optional<UserAnimal> findByInven(UUID byUserUUID, Long animal);

    @Modifying
    @Query("DELETE FROM UserAnimal ua WHERE ua.user.userUUID = :byUserUUID AND ua.animal.animalId = :animal")
    void deleteFindByInven(UUID byUserUUID, Long animal);


}
