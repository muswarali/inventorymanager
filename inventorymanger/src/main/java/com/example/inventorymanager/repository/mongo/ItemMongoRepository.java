package com.example.inventorymanager.repository.mongo;

import java.util.Collections;
import java.util.List;

import org.bson.Document;

import com.example.inventorymanger.model.Item;
import com.example.inventorymanger.repository.ItemRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

public class ItemMongoRepository implements ItemRepository {

	public static final String ITEM_COLLECTION_NAME = "item";
	public static final String INVENTORY_DB_NAME = "inventory";
	private MongoCollection<Document> itemCollection;

	public ItemMongoRepository(MongoClient client) {
		itemCollection = client
			.getDatabase(INVENTORY_DB_NAME)
			.getCollection(ITEM_COLLECTION_NAME);
	}

	@Override
	public void save(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Item> findAll() {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public Item findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}
}
