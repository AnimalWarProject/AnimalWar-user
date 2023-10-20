package com.example.aniamlwaruser.controller;

import com.example.aniamlwaruser.domain.request.INVTRequest;
import com.example.aniamlwaruser.service.INVTService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/inventory")
public class INVTController {

    private final INVTService INVTService;
    @PostMapping
    public void insertPlace(INVTRequest invtRequest){

        INVTService.insertAnimal(invtRequest);

    }

}
