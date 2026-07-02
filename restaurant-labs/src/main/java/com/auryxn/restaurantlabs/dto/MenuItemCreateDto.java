package com.auryxn.restaurantlabs.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record MenuItemCreateDto(
        @NotBlank(message = "Name is required") @Size(max = 120) String name,
        @Size(max = 500) String description,
        @NotNull(message = "Price is required") @DecimalMin(value = "0.01", message = "Price must be positive") BigDecimal price,
        boolean available,
        @NotNull(message = "Restaurant is required") Long restaurantId
) {}
