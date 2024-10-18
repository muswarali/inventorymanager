package com.examples.inventorymanger.repository.mongo;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;

import com.example.inventorymanager.repository.mongo.ItemMongoRepository;
import com.example.inventorymanger.model.Item;

import static com.example.inventorymanager.repository.mongo.ItemMongoRepository.ITEM_COLLECTION_NAME;
import static com.example.inventorymanager.repository.mongo.ItemMongoRepository.INVENTORY_DB_NAME;


import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



public class ItemMongoRepositoryTestcontainersIT {


	@ClassRule
	public static final MongoDBContainer mongo =
	new MongoDBContainer("mongo:4.4.3");
	
	private MongoClient client;
	private ItemMongoRepository itemRepository;
	private MongoCollection<Document> itemCollection;
	
	@Before
	public void setup() {
		client = new MongoClient(
				new ServerAddress(
					mongo.getHost(),
					mongo.getFirstMappedPort()));
			itemRepository = new ItemMongoRepository(client);
			MongoDatabase database = client.getDatabase(INVENTORY_DB_NAME);
			// make sure we always start with a clean database
			database.drop();
			itemCollection = database.getCollection(ITEM_COLLECTION_NAME);
	}
	
	@After
	public void tearDown() {
		client.close();
	}
	
	
	@Test
	public void testFindAll() {
		addTestItemToDatabase("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		addTestItemToDatabase("2", "Laptop", 1, 99.99, "gaming laptop");
		assertThat(itemRepository.findAll())
			.containsExactly(
				new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop"),
				new Item("2", "Laptop", 1, 99.99, "gaming laptop"));
	}

	@Test
	public void testFindById() {
		addTestItemToDatabase("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		addTestItemToDatabase("2", "Laptop", 1, 99.99, "gaming laptop");
		assertThat(itemRepository.findById("2"))
			.isEqualTo(new Item("2", "Laptop", 1, 99.99, "gaming laptop"));
	}

	@Test
	public void testSave() {
		Item student = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		itemRepository.save(student);
		assertThat(readAllItemFromDatabase())
			.containsExactly(student);
	}

	@Test
	public void testDelete() {
		addTestItemToDatabase("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		itemRepository.delete("1");
		assertThat(readAllItemFromDatabase())
			.isEmpty();
	}
	
	@Test
	public void testUpdate() {
	    Item originalItem = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	    itemRepository.save(originalItem);
	   
	    assertThat(readAllItemFromDatabase()).containsExactly(originalItem);
	    
	    Item updatedItem = new Item("1", "Laptop", 15, 899.99, "Updated laptop");
	    itemRepository.update(updatedItem);
	    assertThat(readAllItemFromDatabase()).containsExactly(updatedItem);
	}
	
	
	private void addTestItemToDatabase(String id, String name, int quantity, double price, String description) {
		itemCollection.insertOne(
				new Document()
					.append("id", id)
					.append("name", name)
					.append("quantity", quantity)
					.append("price", price)
					.append("description", description));
	}
	
	private List<Item> readAllItemFromDatabase() {
		return StreamSupport.
			stream(itemCollection.find().spliterator(), false)
				.map(d -> new Item(
				        "" + d.get("id"),
				        "" + d.get("name"),
				        ((Number) d.get("quantity")).intValue(),   // Cast to Number and then to int
				        ((Number) d.get("price")).doubleValue(),   // Cast to Number and then to double
				        "" + d.get("description")))
				.collect(Collectors.toList());
	}

}
