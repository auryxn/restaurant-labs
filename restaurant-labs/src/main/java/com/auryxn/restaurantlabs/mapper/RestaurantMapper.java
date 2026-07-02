package com.auryxn.restaurantlabs.mapper;

import com.auryxn.restaurantlabs.dto.*;
import com.auryxn.restaurantlabs.entity.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {
    public RestaurantReadDto toReadDto(Restaurant restaurant) {
        return new RestaurantReadDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCuisine(),
                restaurant.getMenuItems() == null ? 0 : restaurant.getMenuItems().size()
        );
    }

    public Restaurant toEntity(RestaurantCreateDto dto) {
        return Restaurant.builder()
                .name(dto.name())
                .address(dto.address())
                .cuisine(dto.cuisine())
                .build();
    }

    public void updateEntity(Restaurant restaurant, RestaurantUpdateDto dto) {
        restaurant.setName(dto.name());
        restaurant.setAddress(dto.address());
        restaurant.setCuisine(dto.cuisine());
    }
}
