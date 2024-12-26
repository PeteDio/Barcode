package com.barcode.barcode.service;

import com.barcode.barcode.model.Item;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ItemService {
    List<Item> findAll();

    Item getItemById(int id);

    Item save(Item item);

    Item findByBarcodesIn(String barcode);
}
