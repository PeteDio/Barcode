package com.barcode.barcode.service;

import com.barcode.barcode.model.Item;
import com.barcode.barcode.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> findAll() {
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
    public Item findByBarcodesIn(String barcode) {
        return itemRepository.findByBarcodesIn(barcode);
    }

}
