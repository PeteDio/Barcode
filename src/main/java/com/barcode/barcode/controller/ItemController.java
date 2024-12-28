package com.barcode.barcode.controller;

import com.barcode.barcode.model.Item;
import com.barcode.barcode.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.findAll();

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
     *         - If the item is found, the status code is set to HttpStatus. OK and the response body contains the item data.
     *         - If the item is not found, the status code is set to HttpStatus.NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable int id) {
        var item = itemService.getItemById(id);
        if(item == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /**
     * Creates a new item.
     * <p>
     * This API endpoint creates a new item and persists it to the database.
     *
     * @param item The item data to be created. The item data is provided in the request body.
     * @return A ResponseEntity object containing the created item and the HTTP status code.
     *         - If the item is created successfully, the status code is set to HttpStatus. CREATED and the response body contains the created item data.
     */
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        Item savedItem = itemService.save(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    /**
     * Updates an existing item's barcode(s).
     * <p>
     * This API endpoint updates the barcode(s) of an existing item identified by its ID.
     * You can provide one or more barcodes in the request body. Each barcode must adhere to the following format:
     * one or more digits, optionally separated by dashes.
     *
     * @param id The unique identifier of the item to update.
     * @param barcodesToAdd A list of new barcodes for the item.
     * @return A ResponseEntity object containing the updated item and the HTTP status code.
     *         - If the ID is invalid or the item is not found, the status code is set to HttpStatus.BAD_REQUEST.
     *         - If the provided barcode format is invalid, the status code is set to HttpStatus.BAD_REQUEST.
     *         - If any of the provided barcodes already exist in the system, the status code is set to HttpStatus.CONFLICT and no update will be performed.
     *         - If the item is updated successfully, the status code is set to HttpStatus.OK and the response body contains the updated item data.
     *
     * @throws IllegalArgumentException If an invalid barcode format is provided.
     */
    @PutMapping("/{id}/barcodes")
    public ResponseEntity<Item> addBarcodeToItem(@PathVariable int id, @RequestBody List<String> barcodesToAdd) {
        Item item = itemService.getItemById(id);

        if (item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Item updatedItem = new Item(item.getId(),item.getName(),item.getBarcodes());
        for (String barcode : barcodesToAdd) {
            if (itemService.hasBarcode(barcode)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            updatedItem.addBarcode(barcode);
        }
        itemService.save(updatedItem);

        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }
    /**
     * Searches for items by a list of barcodes.
     * <p>
     * This API endpoint retrieves items from the database that match any of the provided barcodes in the request body.
     *
     * @param barcode A string barcode to search for.
     * @return A ResponseEntity object containing:
     *         - A list of matching items on success (HTTP status 200 OK).
     *         - An empty body with HTTP status 400 Bad Request if no barcodes are provided or the list is empty.
     *         - An empty body with HTTP status 404 Not Found if no items are found for the provided barcodes.
     */
    @GetMapping("/search")
    public ResponseEntity<Item> searchItemsByBarcode(@RequestBody String barcode) {
        if (barcode == null || barcode.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Item> item = Optional.ofNullable(itemService.findByBarcodesIn(barcode));

        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

