package com.auryxn.restaurantlabs.controller.api;

import com.auryxn.restaurantlabs.dto.*;
import com.auryxn.restaurantlabs.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemRestController {
    private final MenuItemService menuItemService;

    @GetMapping
    public List<MenuItemReadDto> findAll(@RequestParam(required = false) String query, @RequestParam(required = false) Long restaurantId) {
        return menuItemService.findAll(query, restaurantId);
    }

    @GetMapping("/{id}")
    public MenuItemReadDto findById(@PathVariable Long id) {
        return menuItemService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItemReadDto create(@Valid @RequestBody MenuItemCreateDto dto) {
        return menuItemService.create(dto);
    }

    @PutMapping("/{id}")
    public MenuItemReadDto update(@PathVariable Long id, @Valid @RequestBody MenuItemUpdateDto dto) {
        return menuItemService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        menuItemService.delete(id);
    }
}
