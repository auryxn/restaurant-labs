package com.auryxn.restaurantlabs.service;

import com.auryxn.restaurantlabs.dto.*;
import com.auryxn.restaurantlabs.entity.Restaurant;
import com.auryxn.restaurantlabs.exception.ResourceNotFoundException;
import com.auryxn.restaurantlabs.mapper.RestaurantMapper;
import com.auryxn.restaurantlabs.repository.RestaurantRepository;
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
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public List<RestaurantReadDto> findAll(String query) {
        log.debug("Searching restaurants with query={}", query);
        List<Restaurant> restaurants = (query == null || query.isBlank())
                ? restaurantRepository.findAll()
                : restaurantRepository.findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(query, query);
        return restaurants.stream().map(restaurantMapper::toReadDto).toList();
    }

    public RestaurantReadDto findById(Long id) {
        return restaurantMapper.toReadDto(getEntity(id));
    }

    public Restaurant getEntity(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant with id " + id + " was not found"));
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public RestaurantReadDto create(RestaurantCreateDto dto) {
        log.info("Creating restaurant {}", dto.name());
        Restaurant saved = restaurantRepository.save(restaurantMapper.toEntity(dto));
        return restaurantMapper.toReadDto(saved);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public RestaurantReadDto update(Long id, RestaurantUpdateDto dto) {
        log.info("Updating restaurant id={}", id);
        Restaurant restaurant = getEntity(id);
        restaurantMapper.updateEntity(restaurant, dto);
        return restaurantMapper.toReadDto(restaurant);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        log.warn("Deleting restaurant id={}", id);
        Restaurant restaurant = getEntity(id);
        restaurantRepository.delete(restaurant);
    }
}
