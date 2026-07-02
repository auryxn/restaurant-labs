package com.auryxn.restaurantlabs.repository;

import com.auryxn.restaurantlabs.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
    List<MenuItem> findByRestaurantId(Long restaurantId);
}
