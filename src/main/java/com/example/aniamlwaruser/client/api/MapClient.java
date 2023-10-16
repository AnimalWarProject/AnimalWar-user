package com.example.aniamlwaruser.client.api;

import com.example.aniamlwaruser.domain.dto.LandFormDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("MAP-SERVICE")
public interface MapClient {
    @GetMapping("api/v1/map/{id}/landform")
    ResponseEntity<LandFormDto> getLandFormByUserId(@PathVariable String id);
}