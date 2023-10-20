package com.example.aniamlwaruser.controller;

import com.example.aniamlwaruser.domain.request.AnimalINBTRequest;
import com.example.aniamlwaruser.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/place")
public class PlaceController {

    private final PlaceService placeService;
    @PostMapping
    public void insertPlace(AnimalINBTRequest animalINBTRequest){



    }

}
