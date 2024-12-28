package com.barcode.barcode.service;

import com.barcode.barcode.model.Item;
import com.barcode.barcode.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(int id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item getByBarcodesIn(String barcode) {
        return itemRepository.findByBarcodesIn(barcode);
    }

    @Override
    public Boolean hasBarcode(String barcode) {
        Optional<Item> item = Optional.ofNullable(itemRepository.findByBarcodesIn(barcode));
        return item.isPresent();
    }

    @Override
    public List<Item> getByName(String name) {
        return itemRepository.findByNameContainingIgnoreCase(name);
    }
    public void updateItem(Item existingItem, Item updatedItem) {
        if (existingItem == null || updatedItem == null) {
            throw new IllegalArgumentException("Existing item and updated item cannot be null.");
        }

        // Update fields individually to avoid overwriting unintended fields (important!)
        if (updatedItem.getName() != null) {
            existingItem.setName(updatedItem.getName());
        }

        if (updatedItem.getBarcodes() != null) {
            existingItem.setBarcodes(updatedItem.getBarcodes());
        }
        // Add other fields as needed (e.g., description, etc.)

        itemRepository.save(existingItem);
    }
    @Override
    public Optional<Item> findById(Integer id) {
        return itemRepository.findById(id);
    }

}
