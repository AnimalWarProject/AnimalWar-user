package com.example.aniamlwaruser.domain.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAnimalRequest {

    private Long itemId;
    private String name;
    private String grade;
    private Integer attackPower;
    private Integer defencePower;
    private Integer life;
    private String species;
    private String imagePath;
    private Integer buff;
    private Integer price;


}
