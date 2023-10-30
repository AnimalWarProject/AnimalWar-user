package com.example.aniamlwaruser.service;

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

    // 매시간 유저 자원 반영
    @Scheduled(cron = "0 0 * * * ?")
    @Async
    public void updateResourceHourly() {
        List<User> allUsers = userRepository.findAll();

        for (User user : allUsers) {
            int totalFoodRate = 0;
            int totalIronRate = 0;
            int totalWoodRate = 0;

            List<UserBuilding> userBuildings = user.getBuildingInventory();
            for (UserBuilding userBuilding : userBuildings) {
                if (userBuilding.getPlacedQuantity() > 0) {
                    totalFoodRate += userBuilding.getBuilding().getFoodRate() * userBuilding.getPlacedQuantity();
                    totalIronRate += userBuilding.getBuilding().getIronRate() * userBuilding.getPlacedQuantity();
                    totalWoodRate += userBuilding.getBuilding().getWoodRate() * userBuilding.getPlacedQuantity();
                }
            }
            user.addFood(totalFoodRate);
            user.addIron(totalIronRate);
            user.addWood(totalWoodRate);
        }
        userRepository.saveAll(allUsers);
    }
}

