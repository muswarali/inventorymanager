package com.example.inventorymanger.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ItemTest {

	@Test
	   public void testItemConstructorAndGetters() {
        // Define initial values for an item
        String id = "1";
        String name = "Laptop";
        int quantity = 10;

        // Create an item object
        Item item = new Item(id, name, quantity);

        // Assert that the values are correctly set
        assertEquals(id, item.getId());
        assertEquals(name, item.getName());
        assertEquals(quantity, item.getQuantity());
    }
}
