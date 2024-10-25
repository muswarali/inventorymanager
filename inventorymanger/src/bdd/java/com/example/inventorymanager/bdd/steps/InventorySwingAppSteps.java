package com.example.inventorymanager.bdd.steps;

import org.bson.Document;
import com.mongodb.MongoClient;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InventorySwingAppSteps {

	private static final String DB_NAME = "test-db";
	private static final String COLLECTION_NAME = "test-collection";

	private MongoClient mongoClient;

	@Before
	public void setUp() {
		mongoClient = new MongoClient();
		// always start with an empty database
		mongoClient.getDatabase(DB_NAME).drop();
	}

	@After
	public void tearDown() {
		mongoClient.close();
	}

	@Given("The database contains an item with id {string}, name {string}, quantity {string}, price {string}, and description {string}")
	public void the_database_contains_an_item_with_id_name_quantity_price_and_description(String id, String name,
			String quantity, String price, String description) {
		// Write code here that turns the phrase above into concrete actions
		mongoClient.getDatabase(DB_NAME).getCollection(COLLECTION_NAME)
				.insertOne(new Document().append("id", id)
						.append("name", name)
						.append("quantity", quantity)
						.append("price", price)
						.append("description", description));
		
	}

	@When("The Inventory View is shown")
	public void the_Inventory_View_is_shown() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("The list contains an element with id {string}, name {string}, quantity {string}, price {string}, and description {string}")
	public void the_list_contains_an_element_with_id_name_quantity_price_and_description(String string, String string2,
			String string3, String string4, String string5) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

}
