package com.barcode.barcode.controller;

import com.barcode.barcode.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
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

    @GetMapping("/edit")
    public String createItemForm() {
        return "items/edit";
    }

    @GetMapping("/error")
    public String handleError(Model model) {
        String errorMessage = "An unexpected error occurred.";

        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

}