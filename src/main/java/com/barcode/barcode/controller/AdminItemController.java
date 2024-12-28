package com.barcode.barcode.controller;

import com.barcode.barcode.model.Item;
import com.barcode.barcode.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Optional;

@Controller
@RequestMapping("/admin/items")
public class AdminItemController {

    private final ItemService itemService;

    public AdminItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String listItems(Model model) {
        model.addAttribute("items", itemService.getAll());
        return "items/list";
    }

    @GetMapping("/{id}/edit")
    public String editItem(@PathVariable Integer id, Model model) {
        Optional<Item> item = itemService.findById(id);
        if (item.isPresent()) {
            model.addAttribute("item", item.get());
            return "items/edit";
        } else {
            return "redirect:/admin/items"; // Redirect to the item list
        }
    }

    @PutMapping("/{id}")
    public String updateItem(@PathVariable String id, @Valid @ModelAttribute("item") Item item, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "items/edit";
        }

        // Update the item in the service
        itemService.save(item);

        return "redirect:/admin/items";
    }

    @GetMapping("/error")
    public String handleError(Model model) {
        String errorMessage = "An unexpected error occurred.";

        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

}
