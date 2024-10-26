package com.example.inventorymanager.bdd.steps;

import static org.assertj.swing.launcher.ApplicationLauncher.application;

import static org.assertj.core.api.Assertions.assertThat;


import javax.swing.JFrame;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
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

	private FrameFixture window;

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
		if (window != null)
			window.cleanUp();
	}

	@Given("The database contains an item with id {string}, name {string}, quantity {int}, price {double}, and description {string}")
	public void the_database_contains_an_item_with_id_name_quantity_price_and_description(String id, String name,
			int quantity, double price, String description) {
		// Write code here that turns the phrase above into concrete actions
		mongoClient.getDatabase(DB_NAME).getCollection(COLLECTION_NAME)
				.insertOne(new Document().append("id", id).append("name", name).append("quantity", quantity)
						.append("price", price).append("description", description));

	}

	@When("The Inventory View is shown")
	public void the_Inventory_View_is_shown() {
		// Write code here that turns the phrase above into concrete actions
		// start the Swing application
		application("com.example.inventorymanager.app.swing.InventorySwingApp")
				.withArgs("--db-name=" + DB_NAME, "--db-collection=" + COLLECTION_NAME).start();
		// get a reference of its JFrame
		window = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
			@Override
			protected boolean isMatching(JFrame frame) {
				return "Inventory Manager".equals(frame.getTitle()) && frame.isShowing();
			}
		}).using(BasicRobot.robotWithCurrentAwtHierarchy());
	}

	@Then("The list contains an element with id {string}, name {string}, quantity {int}, price {double}, and description {string}")
	public void the_list_contains_an_element_with_id_name_quantity_price_and_description(String id, String name,
			int quantity, double price, String description) {
		// Write code here that turns the phrase above into concrete actions
		assertThat(window.list().contents()).anySatisfy(e -> assertThat(e).contains(id, name,String.valueOf(quantity),String.valueOf(price),description));
	}

}
