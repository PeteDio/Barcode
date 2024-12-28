package com.barcode.barcode.repository;

import com.barcode.barcode.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, Integer> {
    Item findByBarcodesIn(String barcodes);
}
