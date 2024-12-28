package com.barcode.barcode.service;

import com.barcode.barcode.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAll();

    Item getItemById(int id);

    Item save(Item item);

    Item getByBarcodesIn(String barcode);

    Boolean hasBarcode(String barcode);

    List<Item> getByName(String name);
}
