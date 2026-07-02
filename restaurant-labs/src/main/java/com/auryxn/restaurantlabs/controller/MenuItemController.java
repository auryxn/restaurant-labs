package com.auryxn.restaurantlabs.controller;

import com.auryxn.restaurantlabs.dto.*;
import com.auryxn.restaurantlabs.service.MenuItemService;
import com.auryxn.restaurantlabs.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuItemController {
    private final MenuItemService menuItemService;
    private final RestaurantService restaurantService;

    @GetMapping
    public String list(@RequestParam(required = false) String query, @RequestParam(required = false) Long restaurantId, Model model) {
        model.addAttribute("items", menuItemService.findAll(query, restaurantId));
        model.addAttribute("restaurants", restaurantService.findAll(null));
        model.addAttribute("query", query);
        model.addAttribute("restaurantId", restaurantId);
        return "menu/list";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("item", menuItemService.findById(id));
        return "menu/detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("item", new MenuItemCreateDto("", "", BigDecimal.ONE, true, null));
        model.addAttribute("restaurants", restaurantService.findAll(null));
        return "menu/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("item") MenuItemCreateDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("restaurants", restaurantService.findAll(null));
            return "menu/form";
        }
        menuItemService.create(dto);
        return "redirect:/menu";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        MenuItemReadDto dto = menuItemService.findById(id);
        model.addAttribute("item", new MenuItemUpdateDto(dto.name(), dto.description(), dto.price(), dto.available(), dto.restaurantId()));
        model.addAttribute("restaurants", restaurantService.findAll(null));
        model.addAttribute("id", id);
        return "menu/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("item") MenuItemUpdateDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("restaurants", restaurantService.findAll(null));
            model.addAttribute("id", id);
            return "menu/form";
        }
        menuItemService.update(id, dto);
        return "redirect:/menu";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            menuItemService.delete(id);
            attributes.addFlashAttribute("message", "Menu item deleted");
        } catch (AccessDeniedException ex) {
            attributes.addFlashAttribute("error", "Only ADMIN can delete menu items");
        }
        return "redirect:/menu";
    }
}
