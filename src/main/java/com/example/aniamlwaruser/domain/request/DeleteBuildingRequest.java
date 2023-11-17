package com.example.aniamlwaruser.domain.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBuildingRequest {

    private Long itemId;
    private String name;
    private String grade;
    private Integer attackPower;
    private Integer defencePower;
    private Integer life;
    private Integer woodRate;
    private Integer ironRate;
    private Integer foodRate;
    private String imagePath;
    private String buildingType;
    private Integer price;


}
