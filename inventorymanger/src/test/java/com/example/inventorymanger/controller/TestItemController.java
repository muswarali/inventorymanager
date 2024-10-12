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


}
