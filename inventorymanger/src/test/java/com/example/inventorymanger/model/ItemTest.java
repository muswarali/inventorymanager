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
        double price = 999.99;
        String description = "High-end gaming laptop";

        // Create an item object
        Item item = new Item(id, name, quantity,price, description);

        // Assert that the values are correctly set
        assertEquals(id, item.getId());
        assertEquals(name, item.getName());
        assertEquals(quantity, item.getQuantity());
        assertEquals(price, item.getPrice(), 0.001);
        assertEquals(description, item.getDescription());
        
    }
	
	   @Test
	    public void testItemSetters() {
	        Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");

	        item.setId("2");
	        item.setName("Desktop");
	        item.setQuantity(5);
	        item.setPrice(1499.99);
	        item.setDescription("All-in-one desktop computer");

	        assertEquals("2", item.getId());
	        assertEquals("Desktop", item.getName());
	        assertEquals(5, item.getQuantity());
	        assertEquals(1499.99, item.getPrice(), 0.001);
	        assertEquals("All-in-one desktop computer", item.getDescription());
	    }
}
