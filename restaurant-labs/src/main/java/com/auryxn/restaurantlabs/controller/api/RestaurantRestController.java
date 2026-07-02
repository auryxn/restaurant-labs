package com.auryxn.restaurantlabs.controller.api;

import com.auryxn.restaurantlabs.dto.*;
import com.auryxn.restaurantlabs.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantRestController {
    private final RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantReadDto> findAll(@RequestParam(required = false) String query) {
        return restaurantService.findAll(query);
    }

    @GetMapping("/{id}")
    public RestaurantReadDto findById(@PathVariable Long id) {
        return restaurantService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantReadDto create(@Valid @RequestBody RestaurantCreateDto dto) {
        return restaurantService.create(dto);
    }

    @PutMapping("/{id}")
    public RestaurantReadDto update(@PathVariable Long id, @Valid @RequestBody RestaurantUpdateDto dto) {
        return restaurantService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        restaurantService.delete(id);
    }
}
