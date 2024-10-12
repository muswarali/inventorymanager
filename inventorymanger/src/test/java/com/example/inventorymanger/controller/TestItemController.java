package com.example.inventorymanger.controller;

import com.example.inventorymanger.model.Item;
import com.example.inventorymanger.repository.ItemRepository;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class TestItemController {

	private ItemController itemController;
    private ItemRepository itemRepository;
    
    @Before
    public void setUp() {
        // Mock the ItemRepository
        itemRepository = mock(ItemRepository.class);
        // Pass the mocked repository to the controller
        itemController = new ItemController(itemRepository);
    }
    
    @Test
    public void testAddItem() {
        Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
        itemController.addItem(item);
        // Verify that the repository's save method was called
        verify(itemRepository).save(item);
    }
    @Test
    public void testGetAllItems() {
        List<Item> items = Arrays.asList(
            new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop"),
            new Item("2", "Desktop", 5, 799.99, "All-in-one desktop")
        );
        when(itemRepository.findAll()).thenReturn(items);

        List<Item> result = itemController.getAllItems();
        // Verify the method result and interactions
        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());
    }

    @Test
    public void testGetItemById() {
        Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
        when(itemRepository.findById("1")).thenReturn(item);

        Item result = itemController.getItemById("1");
        // Verify the result
        assertNotNull(result);
        assertEquals("Laptop", result.getName());
    }

    @Test
    public void testUpdateItem() {
        Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
        itemController.updateItem(item);
        // Verify that the repository's update method was called
        verify(itemRepository).update(item);
    }

    @Test
    public void testDeleteItem() {
        itemController.deleteItem("1");
        // Verify that the repository's delete method was called
        verify(itemRepository).delete("1");
    }


}
