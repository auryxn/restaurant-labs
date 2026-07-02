package com.auryxn.restaurantlabs.service;

import com.auryxn.restaurantlabs.dto.*;
import com.auryxn.restaurantlabs.entity.MenuItem;
import com.auryxn.restaurantlabs.entity.Restaurant;
import com.auryxn.restaurantlabs.exception.ResourceNotFoundException;
import com.auryxn.restaurantlabs.mapper.MenuItemMapper;
import com.auryxn.restaurantlabs.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantService restaurantService;
    private final MenuItemMapper menuItemMapper;

    public List<MenuItemReadDto> findAll(String query, Long restaurantId) {
        log.debug("Searching menu items query={}, restaurantId={}", query, restaurantId);
        List<MenuItem> items;
        if (restaurantId != null) {
            items = menuItemRepository.findByRestaurantId(restaurantId);
        } else if (query != null && !query.isBlank()) {
            items = menuItemRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
        } else {
            items = menuItemRepository.findAll();
        }
        return items.stream().map(menuItemMapper::toReadDto).toList();
    }

    public MenuItemReadDto findById(Long id) {
        return menuItemMapper.toReadDto(getEntity(id));
    }

    private MenuItem getEntity(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item with id " + id + " was not found"));
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public MenuItemReadDto create(MenuItemCreateDto dto) {
        Restaurant restaurant = restaurantService.getEntity(dto.restaurantId());
        log.info("Creating menu item {} for restaurant id={}", dto.name(), dto.restaurantId());
        return menuItemMapper.toReadDto(menuItemRepository.save(menuItemMapper.toEntity(dto, restaurant)));
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public MenuItemReadDto update(Long id, MenuItemUpdateDto dto) {
        MenuItem item = getEntity(id);
        Restaurant restaurant = restaurantService.getEntity(dto.restaurantId());
        log.info("Updating menu item id={}", id);
        menuItemMapper.updateEntity(item, dto, restaurant);
        return menuItemMapper.toReadDto(item);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        log.warn("Deleting menu item id={}", id);
        menuItemRepository.delete(getEntity(id));
    }
}
