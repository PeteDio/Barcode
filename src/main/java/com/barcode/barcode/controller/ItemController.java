package com.barcode.barcode.controller;

import com.barcode.barcode.model.Item;
import com.barcode.barcode.dto.ItemRequestDTO;
import com.barcode.barcode.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Retrieves all items.
     * <p>
     * This endpoint retrieves a list of all items available in the system.
     *
     * @return A ResponseEntity containing:
     * - A list of Item objects with an HTTP status of 200 (OK) if items are found.
     * - An HTTP status of 204 (NO_CONTENT) if no items are found.
     */
    @GetMapping("/")
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getAll();

        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(items, HttpStatus.OK);
        }
    }

    /**
     * Fetches an item by its ID.
     * <p>
     * This API endpoint retrieves an item from the database based on the provided ID.
     *
     * @param id The unique identifier of the item to retrieve.
     * @return A ResponseEntity object containing the retrieved item and the HTTP status code.
     * - If the item is found, the status code is set to HttpStatus. OK and the response body contains the item data.
     * - If the item is not found, the status code is set to HttpStatus.NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {
        var item = itemService.getItemById(id);
        if (item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /**
     * Creates a new item and persists it to the database.
     * <p>
     * This API endpoint allows creating a new item by providing the item details in a JSON request body.
     * The request body should be of type {@link ItemRequestDTO}.
     *
     * @param newItem The item data to be created. The item data is provided in the request body.
     * @param result  The BindingResult object containing validation errors (if any).
     * @return A ResponseEntity object containing:
     * - The created item's details as a String representation and the HTTP status code of 201 (CREATED)  if the item is created successfully.
     * - An error message with the appropriate HTTP status code (BAD_REQUEST or CONFLICT) if the request is invalid or the item already exists.
     */

    @PostMapping
    public ResponseEntity<String> createItem(@RequestBody ItemRequestDTO newItem, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<String> invalidBarcodes = itemService.isValidBarcode(newItem.getBarcodes());
        if (!invalidBarcodes.isEmpty()) {
            String errorMessage = String.format("Invalid barcode(s): %s", String.join(", ", invalidBarcodes));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        // Check for existing barcodes
        for (String barcode : newItem.getBarcodes()) {
            if (itemService.hasBarcode(barcode)) {
                String errorMessage = String.format("Barcode %s already assigned", barcode);
                return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
            }
        }

        // Check for existing name
        if (itemService.hasName(newItem.getName())) {
            String errorMessage = String.format("Name %s already assigned", newItem.getName());
            return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
        }

        Item createdItem = new Item(newItem.getName(), newItem.getBarcodes());
        Item savedItem = itemService.save(createdItem);
        String successMessage = String.format("Item Successfully saved: %s", savedItem);
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }


    /**
     * Updates an existing item.
     * <p>
     * This endpoint updates the details of an item identified by its ID.
     * The request body should contain the updated item data in JSON format.
     * Only the provided fields in the `updatedItem` will be updated; other fields will remain unchanged.
     *
     * @param id          The ID of the item to update.
     * @param updatedItem The Item object containing the updated data.
     * @return A ResponseEntity containing:
     * - The updated Item object with an HTTP status of 200 (OK) if the update is successful.
     * - An HTTP status of 404 (NOT_FOUND) if an item with the given ID does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable String id, @RequestBody Item updatedItem) {
        Item existingItem = itemService.getItemById(id);

        if (existingItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        itemService.updateItem(existingItem, updatedItem);

        return new ResponseEntity<>(existingItem, HttpStatus.OK);
    }

    /**
     * Updates an existing item's barcode(s).
     * <p>
     * This API endpoint updates the barcode(s) of an existing item identified by its ID.
     * You can provide one or more barcodes in the request body. Each barcode must adhere to the following format:
     * one or more digits, optionally separated by dashes.
     *
     * @param id            The unique identifier of the item to update.
     * @param barcodesToAdd A list of new barcodes for the item.
     * @return A ResponseEntity object containing a message string and the HTTP status code.
     * - If the ID is invalid or the item is not found, the response body is "Item not found" and the status code is set to HttpStatus.BAD_REQUEST.
     * - If the provided barcode format is invalid (during validation), the response body contains the specific error message and the status code is set to HttpStatus.BAD_REQUEST.
     * - If any of the provided barcodes already exist in the system, the response body is "Barcode [existing_barcode] already assigned" and the status code is set to HttpStatus.CONFLICT.
     * - If the item is updated successfully, the response body is "Barcodes updated successfully" and the status code is set to HttpStatus.OK.
     */
    @PutMapping("/{id}/barcodes")
    public ResponseEntity<String> addBarcodeToItem(@PathVariable String id, @RequestBody List<String> barcodesToAdd, @RequestParam(required = false) List<String> existingBarcodes) {
        Item item = itemService.getItemById(id);

        if (item == null) {
            return new ResponseEntity<>("Item not found", HttpStatus.BAD_REQUEST);
        }

        Item updatedItem = new Item(item.getId(), item.getName(), existingBarcodes != null ? existingBarcodes : item.getBarcodes());

        // Validate barcodes (if any)
        List<String> invalidBarcodes = itemService.isValidBarcode(barcodesToAdd);
        if (!invalidBarcodes.isEmpty()) {
            return new ResponseEntity<>(STR."Invalid barcode(s): \{String.join(", ", invalidBarcodes)}", HttpStatus.BAD_REQUEST);
        }

        // Check for existing barcodes
        for (String barcode : barcodesToAdd) {
            if (itemService.hasBarcode(barcode)) {
                return new ResponseEntity<>(STR."Barcode \{barcode} already assigned", HttpStatus.CONFLICT);
            }
        }

        // Add new barcodes (if any)
        if (!barcodesToAdd.isEmpty()) {
            updatedItem.addBarcodes(barcodesToAdd);
        }

        itemService.save(updatedItem);

        return new ResponseEntity<>("Barcodes updated successfully", HttpStatus.OK);
    }

    /**
     * Searches for items by a list of barcodes.
     * <p>
     * This API endpoint retrieves items from the database that match any of the provided barcodes in the request body.
     *
     * @param barcode A string barcode to search for.
     * @return A ResponseEntity object containing:
     * - A list of matching items on success (HTTP status 200 OK).
     * - An empty body with HTTP status 400 Bad Request if no barcodes are provided or the list is empty.
     * - An empty body with HTTP status 404 Not Found if no items are found for the provided barcodes.
     */
    @GetMapping("/search/barcode")
    public ResponseEntity<Item> searchItemsByBarcode(@RequestBody String barcode) {
        if (barcode == null || barcode.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Item> item = Optional.ofNullable(itemService.getByBarcodesIn(barcode));

        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Searches for items by name.
     * <p>
     * This API endpoint retrieves items from the database that partially match the provided name in the request path.
     * It uses a case-insensitive search.
     *
     * @param name The name (or part of the name) to search for.
     * @return A ResponseEntity object containing:
     * - A list of matching items on success (HTTP status 200 OK).
     * - An empty body with HTTP status 404 Not Found if no items are found for the provided name.
     */
    @GetMapping("/search/name/")
    public ResponseEntity<List<Item>> searchItemsByName(@RequestBody String name) {
        if (name == null || name.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Item> items = itemService.getByName(name);
        return !items.isEmpty() ? ResponseEntity.ok(items) : ResponseEntity.notFound().build();
    }
}

