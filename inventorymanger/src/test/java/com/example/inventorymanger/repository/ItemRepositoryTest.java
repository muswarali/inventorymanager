package com.example.inventorymanger.repository;

import com.example.inventorymanger.model.Item;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ItemRepositoryTest {
	
	private ItemRepository itemRepository;

	@Before
    public void setUp() {
        // Mocking the ItemRepository interface
        itemRepository = mock(ItemRepository.class);
    }

	
	 @Test
	    public void testSave() {
	        Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	        // Call save on the mocked itemRepository
	        itemRepository.save(item);
	        // Verify that the save method was called
	        verify(itemRepository).save(item);
	    }

	    @Test
	    public void testFindAll() {
	        List<Item> items = new ArrayList<>();
	        items.add(new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop"));
	        // Mock the findAll method to return the list of items
	        when(itemRepository.findAll()).thenReturn(items);

	        List<Item> result = itemRepository.findAll();
	        // Assert that the result is as expected
	        assertEquals(1, result.size());
	        assertEquals("Laptop", result.get(0).getName());
	    }

	    @Test
	    public void testFindById() {
	        Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	        // Mock the findById method to return the item
	        when(itemRepository.findById("1")).thenReturn(item);

	        Item result = itemRepository.findById("1");
	        // Assert that the item returned matches the mock
	        assertNotNull(result);
	        assertEquals("Laptop", result.getName());
	    }

	    @Test
	    public void testUpdate() {
	        Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	        // Call update on the mocked itemRepository
	        itemRepository.update(item);
	        // Verify that the update method was called
	        verify(itemRepository).update(item);
	    }

	    @Test
	    public void testDelete() {
	        // Call delete on the mocked itemRepository
	        itemRepository.delete("1");
	        // Verify that the delete method was called
	        verify(itemRepository).delete("1");
	    }

}
