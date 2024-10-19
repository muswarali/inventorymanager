package com.example.inventorymanager.controller;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.inventorymanager.repository.mongo.ItemMongoRepository;
import com.example.inventorymanger.controller.ItemController;
import com.example.inventorymanger.model.Item;
import com.example.inventorymanger.repository.ItemRepository;
import com.example.inventorymanger.view.InventoryView;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class ItemControllerIT {

	@Mock
	private InventoryView inventoryView;

	private ItemRepository itemRepository;

	private ItemController itemController;

	private AutoCloseable closeable;

	private static int mongoPort =
		Integer.parseInt(System.getProperty("mongo.port", "27017"));

	@Before
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		itemRepository = new ItemMongoRepository(
			new MongoClient(
				new ServerAddress("localhost", mongoPort)));
		// explicit empty the database through the repository
		for (Item item : itemRepository.findAll()) {
			itemRepository.delete(item.getId());
		}
		itemController = new ItemController(itemRepository, inventoryView);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}
	
	@Test
	public void testAllItems() {
		Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		itemRepository.save(item);
		itemController.getAllItems();
		verify(inventoryView)
			.displayItems(asList(item));
	}

	@Test
	public void testNewItem() {
		Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		itemController.addItem(item);
		verify(inventoryView).addItem(item);
	}

	@Test
	public void testDeleteItem() {
		Item itemToDelete = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		itemRepository.save(itemToDelete);
		itemController.deleteItem(itemToDelete);
		verify(inventoryView).deleteItem(itemToDelete);
	}

	@Test
	public void testUpdateItem() {
	    Item originalItem = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	    itemRepository.save(originalItem);

	    Item updatedItem = new Item("1", "Laptop", 15, 899.99, "Updated laptop");
	    itemController.updateItem(updatedItem);

	    verify(inventoryView).updateItem(updatedItem);
	}

}
