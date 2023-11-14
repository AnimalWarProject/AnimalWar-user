package com.example.aniamlwaruser.service;

import com.example.aniamlwaruser.domain.entity.LandForm;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.entity.UserBuilding;
import com.example.aniamlwaruser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final UserRepository userRepository;

    // 매시간 유저 자원을 반영하는 스케줄링된 작업
    @Scheduled(cron = "0 0 * * * ?")
    @Async
    public void updateResourceHourly() {
        List<User> allUsers = userRepository.findAll();

        for (User user : allUsers) {
            user.calculateTotalRates(); // 총 rate를 계산하는 메소드 호출

            // LandForm에 따라 rate 조정
            int foodRateAdjustment = user.getLandForm() == LandForm.LAND ? (int)(user.getTotalFoodRate() * 0.3) : 0;
            int ironRateAdjustment = user.getLandForm() == LandForm.SEA ? (int)(user.getTotalIronRate() * 0.3) : 0;
            int woodRateAdjustment = user.getLandForm() == LandForm.MOUNTAIN ? (int)(user.getTotalWoodRate() * 0.3) : 0;

            // 조정된 rate를 기반으로 자원을 추가
            user.addFood(user.getTotalFoodRate() + foodRateAdjustment);
            user.addIron(user.getTotalIronRate() + ironRateAdjustment);
            user.addWood(user.getTotalWoodRate() + woodRateAdjustment);
        }
        userRepository.saveAll(allUsers); // 변경된 사용자 정보 저장
    }
}
