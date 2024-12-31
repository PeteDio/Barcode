package com.barcode.barcode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document("items")
public class Item {
    @Id
    private String id;
    private String name;
    private List<String> barcodes;

    public Item(String name, List<String> barcodes) {
        this.name = name;
        this.barcodes = barcodes;
    }

    public void addBarcode(String barcode) {
        if (this.barcodes == null) {
            this.barcodes = new ArrayList<>();
        }
        if (barcode != null && !barcode.isEmpty() && !this.barcodes.contains(barcode)) {
            this.barcodes.add(barcode);
        }
    }

    public void addBarcodes(List<String> newBarcodes) {
        if (newBarcodes != null && !newBarcodes.isEmpty()) {
            for (String barcode : newBarcodes) {
                addBarcode(barcode);
            }
        }
    }
}