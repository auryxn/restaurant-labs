package com.auryxn.restaurantlabs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RestaurantCreateDto(
        @NotBlank(message = "Name is required") @Size(max = 120) String name,
        @NotBlank(message = "Address is required") @Size(max = 180) String address,
        @Size(max = 80) String cuisine
) {}
