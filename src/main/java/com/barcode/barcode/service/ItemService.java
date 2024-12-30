package com.barcode.barcode.service;

import com.barcode.barcode.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAll();

    Item getItemById(Integer id);

    Item save(Item item);

    Item getByBarcodesIn(String barcode);

    Boolean hasBarcode(String barcode);

    List<Item> getByName(String name);

    void updateItem(Item existingItem, Item updatedItem);

    List<String> isValidBarcode(List<String> Barcode);
}
