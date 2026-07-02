package com.auryxn.restaurantlabs.mapper;

import com.auryxn.restaurantlabs.dto.*;
import com.auryxn.restaurantlabs.entity.MenuItem;
import com.auryxn.restaurantlabs.entity.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {
    public MenuItemReadDto toReadDto(MenuItem item) {
        return new MenuItemReadDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.isAvailable(),
                item.getRestaurant().getId(),
                item.getRestaurant().getName()
        );
    }

    public MenuItem toEntity(MenuItemCreateDto dto, Restaurant restaurant) {
        return MenuItem.builder()
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .available(dto.available())
                .restaurant(restaurant)
                .build();
    }

    public void updateEntity(MenuItem item, MenuItemUpdateDto dto, Restaurant restaurant) {
        item.setName(dto.name());
        item.setDescription(dto.description());
        item.setPrice(dto.price());
        item.setAvailable(dto.available());
        item.setRestaurant(restaurant);
    }
}
