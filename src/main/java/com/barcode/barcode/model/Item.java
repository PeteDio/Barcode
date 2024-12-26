package com.barcode.barcode.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "items")
public class Item {
    @Id
    private int id;
    private String name;
    private List<String> barcodes;

    // Constructors (required for Spring Data)
    public Item() {}

    public Item(int id, String name, List<String> barcodes) {
        this.id = id;
        this.name = name;
        this.barcodes = barcodes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getBarcodes() { return barcodes; }
    public void setBarcodes(List<String> barcodes) { this.barcodes = barcodes; }

}
