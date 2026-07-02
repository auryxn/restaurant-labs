package com.auryxn.restaurantlabs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @NotBlank @Size(min = 3, max = 80) String username,
        @NotBlank @Size(min = 4, max = 120) String password
) {}
