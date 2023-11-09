package com.example.aniamlwaruser.repository;

import com.example.aniamlwaruser.domain.entity.UserAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAnimalRepository extends JpaRepository<UserAnimal, Long> {

    Optional<UserAnimal> findByAnimal_AnimalId(Long animalId);

    void deleteAllByAnimal_AnimalIdIn(List<Long> userAnimalList);
}
