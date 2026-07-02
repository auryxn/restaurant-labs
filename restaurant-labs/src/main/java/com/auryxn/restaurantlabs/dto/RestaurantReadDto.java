package com.auryxn.restaurantlabs.dto;

public record RestaurantReadDto(Long id, String name, String address, String cuisine, int menuItemsCount) {}
