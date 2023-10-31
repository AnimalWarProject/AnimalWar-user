package com.example.aniamlwaruser.service;

import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.response.NickNameResponse2;
import com.example.aniamlwaruser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final UserRepository userRepository;

    public List<NickNameResponse2> findTopUserByBattlePoint(){
        return userRepository.findTopUserByBattlePoint();
    }

    public List<NickNameResponse2> findTopUserByPower(){
        List<User> topUserByPower = userRepository.findTopUserByPower();


        return topUserByPower.stream().map(user ->new NickNameResponse2(
                user.getNickName()
        ) ).toList();

    }

    public List<NickNameResponse2> findTopUserByGold(){
        return userRepository.findTopUserByGold();
    }


    @Scheduled(cron = "0 0 9 ? * MON")
    @Async
    public void distributeWeeklyRewards() {
        List<User> topUsers = userRepository.findTopUserByPower();
        for (int i = 0; i < topUsers.size(); i++) {
            User user = topUsers.get(i);
            if (i == 0) {
                user.addGold(100000);
            } else if (i == 1) {
                user.addGold(60000);
            } else if (i == 2) {
                user.addGold(40000);
            } else if (i < 10) {
                user.addGold(30000);
            } else if (i < 100) {
                user.addGold(20000);
            } else if (i < 1000) {
                user.addGold(10000);
            }
        }
        userRepository.saveAll(topUsers);
    }
}
