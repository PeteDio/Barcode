package com.barcode.barcode.services;

import com.barcode.barcode.model.Item;
import com.barcode.barcode.repository.ItemRepository;
import com.barcode.barcode.service.ItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService ;

    @Test
    void getAllItems()
    {
        Item item= new Item( "1","Beans",new ArrayList<>());
        Item item2= new Item("2","Chicken",new ArrayList<>());

        given(itemRepository.findAll())
                .willReturn(List.of(item,item2));
        var  itemList = itemService.getAll();
        assertThat(itemList).isNotNull();
        assertThat(itemList.size()).isEqualTo(2);
    }

    @Test
    public void testGetItemById() {
        String id = "123";
        Item expectedItem = new Item(id, "Test Item", new ArrayList<>());
        given(itemRepository.findById(id)).willReturn(expectedItem);

        Item actualItem = itemService.getItemById(id);

        assertThat(actualItem).isNotNull();
        assertThat(actualItem).isEqualTo(expectedItem);
    }

    @Test
    public void testSave() {
        Item newItem = new Item("456", "New Item", new ArrayList<>());
        given(itemRepository.save(newItem)).willReturn(newItem);

        Item savedItem = itemService.save(newItem);

        assertThat(savedItem).isNotNull();
        assertThat(savedItem).isEqualTo(newItem);
        verify(itemRepository).save(newItem);
    }
    @Test
    public void testGetByBarcodesIn() {
        String barcode = "12345";
        Item expectedItem = new Item("789", "Barcode Item", List.of(barcode));
        given(itemRepository.findByBarcodesIn(barcode)).willReturn(expectedItem);

        Item actualItem = itemService.getByBarcodesIn(barcode);

        assertThat(actualItem).isNotNull();
        assertThat(actualItem).isEqualTo(expectedItem);
    }

    @Test
    public void testHasBarcode_ExistingItem() {
        String barcode = "54321";
        var existingItem = new Item("987", "Barcode Item", List.of(barcode));
        given(itemRepository.findByBarcodesIn(barcode)).willReturn(existingItem);

        boolean hasBarcode = itemService.hasBarcode(barcode);

        assertThat(hasBarcode).isTrue();
    }
    @Test
    public void testHasBarcode_NonexistentItem() {
        String barcode = "00000";

        boolean hasBarcode = itemService.hasBarcode(barcode);

        assertThat(hasBarcode).isFalse();
    }
    @Test
    public void testGetByName() {
        String name = "Search Item";
        List<Item> expectedItems = List.of(new Item("101", name, new ArrayList<>()));
        given(itemRepository.findByNameContainingIgnoreCase(name)).willReturn(expectedItems);
        List<Item> actualItems = itemService.getByName(name);

        assertThat(actualItems).isNotEmpty();
        assertThat(actualItems).isEqualTo(expectedItems);
    }

    @Test
    public void testUpdateItem_NullArguments() {
        try {
            itemService.updateItem(null, null);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Existing item and updated item cannot be null.");
            return;
        }
        fail("Expected an IllegalArgumentException");
    }

    @Test
    public void testIsValidBarcode_ValidBarcodes() {
        List<String> barcodes = List.of("12345", "67890");
        List<String> expectedValid = new ArrayList<>();
        given(itemService.isValidBarcode(barcodes)).willReturn(expectedValid);

        List<String> invalidBarcodes = itemService.isValidBarcode(barcodes);

        assertThat(invalidBarcodes).isEmpty();
    }

    @Test
    public void testIsValidBarcode_InvalidBarcodes() {
        List<String> barcodes = List.of("12345", "abc", "67890");
        List<String> expectedInvalid = List.of("abc");

        List<String> invalidBarcodes = itemService.isValidBarcode(barcodes);

        assertThat(invalidBarcodes).isEqualTo(expectedInvalid);
    }

    @Test
    public void testHasName_ExistingName() {
        String name = "Existing Name";
        given(itemRepository.existsByName(name)).willReturn(true);
        boolean hasName = itemService.hasName(name);

        assertThat(hasName).isTrue();
    }

    @Test
    public void testHasName_NonexistentName() {
        String name = "Nonexistent Name";

        boolean hasName = itemService.hasName(name);

        assertThat(hasName).isFalse();
    }

    @Test
    public void testDelete() {
        Item itemToDelete = new Item("111", "Item to Delete", new ArrayList<>());
        itemService.delete(itemToDelete);

        verify(itemRepository).delete(itemToDelete);
    }

    @Test
    public void testGetItemCount() {
        long expectedCount = 10L;
        given(itemRepository.count()).willReturn(expectedCount);

        long actualCount = itemService.getItemCount();

        assertThat(actualCount).isEqualTo(expectedCount);
    }

}