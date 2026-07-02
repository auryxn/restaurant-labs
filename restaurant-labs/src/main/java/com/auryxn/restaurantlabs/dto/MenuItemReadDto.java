package com.auryxn.restaurantlabs.dto;

import java.math.BigDecimal;

public record MenuItemReadDto(Long id, String name, String description, BigDecimal price, boolean available, Long restaurantId, String restaurantName) {}
