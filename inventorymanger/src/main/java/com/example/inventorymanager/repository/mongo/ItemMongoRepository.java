package com.example.inventorymanager.repository.mongo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.example.inventorymanger.model.Item;
import com.example.inventorymanger.repository.ItemRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

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
		itemCollection.insertOne(
				new Document()
				.append("id", item.getId())
				.append("name", item.getName())
				.append("quantity", item.getQuantity())
				.append("price", item.getPrice())
				.append("description", item.getDescription()));
	}

	@Override
	public List<Item> findAll() {
		// TODO Auto-generated method stub
		return StreamSupport.
				stream(itemCollection.find().spliterator(), false)
				.map(this::fromDocumentToItem)
				.collect(Collectors.toList());
	}
	
	private Item fromDocumentToItem(Document d) {
		return new Item(
		        "" + d.get("id"),
		        "" + d.get("name"),
		        ((Number) d.get("quantity")).intValue(),   // Cast to Number and then to int
		        ((Number) d.get("price")).doubleValue(),   // Cast to Number and then to double
		        "" + d.get("description")
		    );
	}

	@Override
	public Item findById(String id) {
		// TODO Auto-generated method stub
		Document d = itemCollection.find(Filters.eq("id", id)).first();
		if (d != null)
			return fromDocumentToItem(d);
		return null;
	}

	@Override
	public void update(Item item) {
		// TODO Auto-generated method stub
		Document updatedDocument = new Document()
                .append("name", item.getName())
                .append("quantity", item.getQuantity())
                .append("price", item.getPrice())
                .append("description", item.getDescription());

        itemCollection.updateOne(
            Filters.eq("id", item.getId()),
            new Document("$set", updatedDocument)
        );
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		itemCollection.deleteOne(Filters.eq("id", id));
	}
}
