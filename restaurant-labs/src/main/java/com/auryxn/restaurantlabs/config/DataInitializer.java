package com.auryxn.restaurantlabs.config;

import com.auryxn.restaurantlabs.entity.*;
import com.auryxn.restaurantlabs.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedData(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository, AppUserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                userRepository.save(AppUser.builder().username("user").password(passwordEncoder.encode("user1234")).roles(Set.of(Role.ROLE_USER)).build());
                userRepository.save(AppUser.builder().username("manager").password(passwordEncoder.encode("manager1234")).roles(Set.of(Role.ROLE_MANAGER)).build());
                userRepository.save(AppUser.builder().username("admin").password(passwordEncoder.encode("admin1234")).roles(Set.of(Role.ROLE_ADMIN)).build());
            }
            if (restaurantRepository.count() == 0) {
                Restaurant italian = restaurantRepository.save(Restaurant.builder().name("Bella Roma").address("Vilnius Old Town 12").cuisine("Italian").build());
                Restaurant sushi = restaurantRepository.save(Restaurant.builder().name("Sakura Bar").address("Gedimino pr. 5").cuisine("Japanese").build());
                menuItemRepository.save(MenuItem.builder().name("Margherita Pizza").description("Tomato, mozzarella, basil").price(new BigDecimal("8.90")).available(true).restaurant(italian).build());
                menuItemRepository.save(MenuItem.builder().name("Carbonara").description("Pasta with egg, cheese and bacon").price(new BigDecimal("10.50")).available(true).restaurant(italian).build());
                menuItemRepository.save(MenuItem.builder().name("Salmon Sushi Set").description("12 pieces with salmon").price(new BigDecimal("14.00")).available(true).restaurant(sushi).build());
            }
        };
    }
}
