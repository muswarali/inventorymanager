package com.example.inventorymanger.repository.mongo;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;
import java.net.InetSocketAddress;
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
import static com.example.inventorymanager.repository.mongo.ItemMongoRepository.INVENTORY_DB_NAME;
import static com.example.inventorymanager.repository.mongo.ItemMongoRepository.ITEM_COLLECTION_NAME;


public class ItemMongoRepositoryTest {

	
	private static MongoServer server;
	private static InetSocketAddress serverAddress;
	
	private MongoClient client;
	private ItemMongoRepository itemMongoRepository;
	private MongoCollection<Document> itemCollection;
	
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
		itemMongoRepository = new ItemMongoRepository(client);
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
	


}
