package com.barcode.barcode.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ItemTest {
    @Test
    public void testItemName() {
        Item item1 = new Item();
        item1.setId(0);
        item1.setName("abcd");
        item1.setBarcodes(new ArrayList<>());

        assertEquals("abcd", item1.getName(), "Item name should be 'abcd'");

        Item item2 = new Item();
        item2.setId(1);
        item2.setName("efgh");
        item2.setBarcodes(new ArrayList<>());
        assertNotEquals("abcd", item2.getName(), "Item name should not be 'abcd'");
        assertEquals("efgh", item2.getName(), "Item name should be 'efgh'");
    }

    @Test
    public void testEmptyItemName(){
        Item item3 = new Item();
        item3.setId(2);
        item3.setBarcodes(new ArrayList<>());
        assertNull(item3.getName());
        item3.setName("");
        assertEquals("", item3.getName());
    }

    @Test
    public void testNullItemName(){
        Item item4 = new Item();
        item4.setId(3);
        item4.setName(null);
        item4.setBarcodes(new ArrayList<>());
        assertNull(item4.getName());
    }


}
