package com.example.aniamlwaruser.repository;

import com.example.aniamlwaruser.domain.entity.Animal;
import com.example.aniamlwaruser.domain.entity.Grade;
import com.example.aniamlwaruser.domain.entity.Species;
import com.example.aniamlwaruser.domain.entity.UserAnimal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal,Long> {

    Optional<Animal> findByName(String animalName);

    List<Animal> findBySpeciesAndGrade(Species species, Grade grade);


}
