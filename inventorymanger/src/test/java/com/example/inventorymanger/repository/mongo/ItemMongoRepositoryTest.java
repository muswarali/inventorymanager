package com.example.inventorymanger.repository.mongo;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.inventorymanager.repository.mongo.ItemMongoRepository;
import com.example.inventorymanger.model.Item;




public class ItemMongoRepositoryTest {

	
	private static MongoServer server;
	private static InetSocketAddress serverAddress;
	
	private MongoClient client;
	private ItemMongoRepository itemMongoRepository;
	private MongoCollection<Document> itemCollection;
	
	public static final String ITEM_COLLECTION_NAME = "item";
	public static final String INVENTORY_DB_NAME = "inventory";
	
	@BeforeClass
	public static void setupServer() {
		server = new MongoServer(new MemoryBackend());
		// bind on a random local port
		serverAddress = server.bind();
		}
		
	@AfterClass
	public static void shutdownServer() {
		server.shutdown();
	}
		
	@Before
	public void setup() {
		client = new MongoClient(new ServerAddress(serverAddress));
		itemMongoRepository = new ItemMongoRepository(client,INVENTORY_DB_NAME,ITEM_COLLECTION_NAME);
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
	public void testFindAllWhenDatabaseIsEmpty() {
	assertThat(itemMongoRepository.findAll()).isEmpty();
	}
	
	@Test
	public void testFindAllWhenDatabaseIsNotEmpty() {
		addTestItemToDatabase("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		addTestItemToDatabase("2", "Laptop", 1, 99.99, "gaming laptop");
		assertThat(itemMongoRepository.findAll())
		.containsExactly(
				new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop"),
				new Item("2", "Laptop", 1, 99.99, "gaming laptop"));
	}
	


	@Test
	public void testFindByIdNotFound() {
		assertThat(itemMongoRepository.findById("1"))
			.isNull();
	}

	@Test
	public void testFindByIdFound() {
		addTestItemToDatabase("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		addTestItemToDatabase("2", "Laptop", 1, 99.99, "gaming laptop");
		assertThat(itemMongoRepository.findById("2"))
			.isEqualTo(new Item("2", "Laptop", 1, 99.99, "gaming laptop"));
	}
	
	@Test
	public void testSave() {
		Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		itemMongoRepository.save(item);
		assertThat(readAllItemFromDatabase())
			.containsExactly(item);
	}

	@Test
	public void testDelete() {
		addTestItemToDatabase("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		itemMongoRepository.delete("1");
		assertThat(readAllItemFromDatabase())
			.isEmpty();
	}
	
	@Test
	public void testUpdate() {
	    Item originalItem = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	    itemMongoRepository.save(originalItem);
	   
	    assertThat(readAllItemFromDatabase()).containsExactly(originalItem);
	    
	    Item updatedItem = new Item("1", "Laptop", 15, 899.99, "Updated laptop");
	    itemMongoRepository.update(updatedItem);
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
