package com.example.aniamlwaruser.controller;

import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rank")
public class RankingController {
    private final RankingService rankingService;

    @GetMapping("/byBattlePoint")
    public List<User> findTopUserByBattlePoint(){
        return rankingService.findTopUserByBattlePoint();
    }

    @GetMapping("/byPower")
    public List<User> findTopUserByPower(){
        return rankingService.findTopUserByPower();
    }

    @GetMapping("/byGold")
    public List<User> findTopUserByGold(){
        return rankingService.findTopUserByGold();
    }
}
