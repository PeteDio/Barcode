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

    /**
     * Displays a list of all items.
     * <p>
     * This endpoint retrieves all items from the database and displays them on a list page.
     *
     * @param model The Model object used to pass data to the view.
     * @return The logical view name "items/list".
     */
    @GetMapping
    public String listItems(Model model) {
        model.addAttribute("items", itemService.getAll());
        return "items/list";
    }

    /**
     * Displays the edit form for a specific item.
     * <p>
     * This endpoint retrieves an item by its ID and displays an edit form populated with the item's data.
     * If the item is not found, the user is redirected to the item list page.
     *
     * @param id The ID of the item to edit.
     * @param model The Model object used to pass data to the view.
     * @return The logical view name "items/edit" if the item is found, or a redirect to "/admin/items" otherwise.
     */
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

    /**
     * Displays the form for creating a new item.
     * <p>
     * This endpoint displays a blank form for creating a new item. An empty Item object is added to the model
     * for form binding.
     *
     * @param model The Model object used to pass data to the view.
     * @return The logical view name "items/create".
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("item", new Item());
        return "items/create";
    }

    /**
     * Displays a generic error page.
     * <p>
     * This endpoint displays a generic error page. It is typically used when other error handling mechanisms redirect to it.
     *
     * @return The logical view name "error".
     */
    @GetMapping("/error")
    public String handleError() {
        return "error";
    }

}
