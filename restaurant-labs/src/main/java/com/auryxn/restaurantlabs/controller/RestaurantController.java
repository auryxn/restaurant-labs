package com.auryxn.restaurantlabs.controller;

import com.auryxn.restaurantlabs.dto.*;
import com.auryxn.restaurantlabs.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping
    public String list(@RequestParam(required = false) String query, Model model) {
        model.addAttribute("restaurants", restaurantService.findAll(query));
        model.addAttribute("query", query);
        return "restaurants/list";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("restaurant", restaurantService.findById(id));
        return "restaurants/detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("restaurant", new RestaurantCreateDto("", "", ""));
        return "restaurants/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("restaurant") RestaurantCreateDto dto, BindingResult result) {
        if (result.hasErrors()) return "restaurants/form";
        restaurantService.create(dto);
        return "redirect:/restaurants";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        RestaurantReadDto dto = restaurantService.findById(id);
        model.addAttribute("restaurant", new RestaurantUpdateDto(dto.name(), dto.address(), dto.cuisine()));
        model.addAttribute("id", id);
        return "restaurants/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("restaurant") RestaurantUpdateDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "restaurants/form";
        }
        restaurantService.update(id, dto);
        return "redirect:/restaurants";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            restaurantService.delete(id);
            attributes.addFlashAttribute("message", "Restaurant deleted");
        } catch (AccessDeniedException ex) {
            attributes.addFlashAttribute("error", "Only ADMIN can delete restaurants");
        }
        return "redirect:/restaurants";
    }
}
