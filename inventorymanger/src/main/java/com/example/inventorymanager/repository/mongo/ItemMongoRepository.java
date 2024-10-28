package com.example.inventorymanager.repository.mongo;

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


	private MongoCollection<Document> itemCollection;
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String QUANTITY = "quantity";
	private static final String PRICE = "price";
	private static final String DESCRIPTION = "description";

	public ItemMongoRepository(MongoClient client , String databaseName , String collectionName) {
		itemCollection = client
			.getDatabase(databaseName)
			.getCollection(collectionName);
	}

	@Override
	public void save(Item item) {
		
		itemCollection.insertOne(
				new Document()
				.append(ID, item.getId())
				.append(NAME, item.getName())
				.append(QUANTITY, item.getQuantity())
				.append(PRICE, item.getPrice())
				.append(DESCRIPTION, item.getDescription()));
	}

	@Override
	public List<Item> findAll() {
		return StreamSupport.
				stream(itemCollection.find().spliterator(), false)
				.map(this::fromDocumentToItem)
				.collect(Collectors.toList());
	}
	
	private Item fromDocumentToItem(Document d) {
		return new Item(
		        "" + d.get(ID),
		        "" + d.get(NAME),
		        ((Number) d.get(QUANTITY)).intValue(),   // Cast to Number and then to int
		        ((Number) d.get(PRICE)).doubleValue(),   // Cast to Number and then to double
		        "" + d.get(DESCRIPTION)
		    );
	}

	@Override
	public Item findById(String id) {
		Document d = itemCollection.find(Filters.eq("id", id)).first();
		if (d != null)
			return fromDocumentToItem(d);
		return null;
	}

	@Override
	public void update(Item item) {
		Document updatedDocument = new Document()
                .append(NAME, item.getName())
                .append(QUANTITY, item.getQuantity())
                .append(PRICE, item.getPrice())
                .append(DESCRIPTION, item.getDescription());

        itemCollection.updateOne(
            Filters.eq("id", item.getId()),
            new Document("$set", updatedDocument)
        );
	}

	@Override
	public void delete(String id) {
		itemCollection.deleteOne(Filters.eq("id", id));
	}
}
