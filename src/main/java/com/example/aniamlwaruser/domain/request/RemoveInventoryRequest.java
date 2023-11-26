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

public class RemoveInventoryRequest {
    private List<RemoveItem> animalItems;
    private List<RemoveItem> buildingItems;

}
