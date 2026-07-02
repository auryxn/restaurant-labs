package com.auryxn.restaurantlabs.dto;

import java.util.Set;

public record AuthResponse(String token, String type, String username, Set<String> roles) {}
