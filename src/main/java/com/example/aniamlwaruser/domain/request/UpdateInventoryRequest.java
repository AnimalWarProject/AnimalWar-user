package com.example.aniamlwaruser.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateInventoryRequest {
    private List<UpdateItem> animalItems;
    private List<UpdateItem> buildingItems;
}
