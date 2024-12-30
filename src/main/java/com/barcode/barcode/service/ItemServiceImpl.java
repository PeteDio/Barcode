package com.barcode.barcode.service;

import com.barcode.barcode.model.Item;
import com.barcode.barcode.repository.ItemRepository;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
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
    public Item getItemById(String id) {
        return itemRepository.findById(id);
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

        if (updatedItem.getName() != null) {
            existingItem.setName(updatedItem.getName());
        }

        if (updatedItem.getBarcodes() != null && !updatedItem.getBarcodes().isEmpty()) {
            existingItem.setBarcodes(updatedItem.getBarcodes());
        }

        itemRepository.save(existingItem);
    }

    @Override
    public List<String> isValidBarcode(List<String> barcodes) {
        List<String> invalidBarcodes = new ArrayList<>();
        if (barcodes == null || barcodes.isEmpty()) {
            return invalidBarcodes;
        }

        String regex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regex);

        for (String barcode : barcodes) {
            Matcher matcher = pattern.matcher(barcode);
            if (!matcher.matches()) {
                invalidBarcodes.add(barcode);
            }
        }
        return invalidBarcodes;
    }

    public boolean hasName(String name) {
        return itemRepository.existsByName(name);
    }

}
