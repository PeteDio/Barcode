package com.barcode.barcode.service;

import com.barcode.barcode.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAll();

    Item getItemById(int id);

    Item save(Item item);

    Item findByBarcodesIn(String barcode);
}
