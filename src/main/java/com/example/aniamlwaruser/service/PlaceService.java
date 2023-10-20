package com.example.aniamlwaruser.service;

import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.entity.UserAnimal;
import com.example.aniamlwaruser.domain.request.AnimalINBTRequest;
import com.example.aniamlwaruser.repository.AnimalINVTRepository;
import com.example.aniamlwaruser.repository.BuildingINVTRepository;
import com.example.aniamlwaruser.repository.BuildingRepository;
import com.example.aniamlwaruser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final UserRepository userRepository;
    private final BuildingRepository buildingRepository;

    private final AnimalINVTRepository animalINVTRepository;
    private final BuildingINVTRepository buildingINVTRepository;


    public void insertAnimalPlace(AnimalINBTRequest animalINBTRequest){


    }

}
