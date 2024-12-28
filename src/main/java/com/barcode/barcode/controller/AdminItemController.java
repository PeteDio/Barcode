package com.barcode.barcode.controller;

import com.barcode.barcode.model.Item;
import com.barcode.barcode.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/create") // Maps to /admin/items/create
    public String showCreateForm(Model model) {
        model.addAttribute("item", new Item()); // Add an empty Item object to the model
        return "items/create"; // Returns the view name for the create form
    }

    @GetMapping("/error")
    public String handleError() {
        return "error";
    }

}
