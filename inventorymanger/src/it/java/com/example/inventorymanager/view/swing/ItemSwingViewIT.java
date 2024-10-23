package com.example.inventorymanager.view.swing;

import static org.assertj.core.api.Assertions.assertThat;
import java.net.InetSocketAddress;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.inventorymanger.controller.ItemController;
import com.example.inventorymanger.model.Item;
import com.example.inventorymanager.repository.mongo.ItemMongoRepository;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

@RunWith(GUITestRunner.class)
public class ItemSwingViewIT extends AssertJSwingJUnitTestCase{

	private static MongoServer server;
	private static InetSocketAddress serverAddress;

	private MongoClient mongoClient;

	private FrameFixture window;
	private InventorySwingView inventorySwingView;
	private ItemController itemController;
	private ItemMongoRepository itemRepository;

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

	@Override
	protected void onSetUp() {
		mongoClient = new MongoClient(new ServerAddress(serverAddress));
		itemRepository = new ItemMongoRepository(mongoClient);
		// explicit empty the database through the repository
		for (Item item : itemRepository.findAll()) {
			itemRepository.delete(item.getId());
		}
		GuiActionRunner.execute(() -> {
			inventorySwingView = new InventorySwingView();
			itemController = new ItemController(itemRepository, inventorySwingView);
			inventorySwingView.setItemController(itemController);
			return inventorySwingView;
		});
		window = new FrameFixture(robot(), inventorySwingView);
		window.show(); // shows the frame to test
	}

	@Override
	protected void onTearDown() {
		mongoClient.close();
	}

	@Test @GUITest
	public void testAllItem() {
		// use the repository to add Items to the database
		Item item1 = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		Item item2 = new Item("2", "Laptop", 12, 599.99, "gaming laptop");
		itemRepository.save(item1);
		itemRepository.save(item2);
		// use the controller's allItems
		GuiActionRunner.execute(
			() -> itemController.getAllItems());
		// and verify that the view's list is populated
		assertThat(window.list().contents())
			.containsExactly(item1.toString(), item2.toString());
	}

	@Test @GUITest
	public void testAddButtonSuccess() {
		window.textBox("idTextBox").enterText("1");
	    window.textBox("nameTextBox").enterText("Laptop");
	    window.textBox("quantityTextBox").enterText("10");
	    window.textBox("priceTextBox").enterText("999.99");
	    window.textBox("descriptionTextBox").enterText("Gaming Laptop");
		window.button(JButtonMatcher.withText("Add Item")).click();
		assertThat(window.list().contents())
			.containsExactly(new Item("1", "Laptop", 10, 999.99, "Gaming Laptop").toString());
	}

	@Test @GUITest
	public void testAddButtonError() {
		itemRepository.save(new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop"));
		window.textBox("idTextBox").enterText("1");
	    window.textBox("nameTextBox").enterText("Laptop");
	    window.textBox("quantityTextBox").enterText("10");
	    window.textBox("priceTextBox").enterText("999.99");
	    window.textBox("descriptionTextBox").enterText("gaming laptop");
		window.button(JButtonMatcher.withText("Add Item")).click();
		assertThat(window.list().contents())
			.isEmpty();
		window.label("errorMessageLabel")
			.requireText("Already existing item with id 1: "
				+ new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop"));
	}

	@Test @GUITest
	public void testDeleteButtonSuccess() {
		// use the controller to populate the view's list...
		itemController.addItem(new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop"));
		// ...with a item to select
		window.list().selectItem(0);
		window.button(JButtonMatcher.withText("Delete Selected")).click();
		assertThat(window.list().contents())
			.isEmpty();
	}

	@Test @GUITest
	public void testDeleteButtonError() {
		// manually add a Item to the list, which will not be in the db
		Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		GuiActionRunner.execute(
			() -> inventorySwingView.getListItemModel().addElement(item));
		window.list().selectItem(0);
		window.button(JButtonMatcher.withText("Delete Selected")).click();
		assertThat(window.list().contents())
			.containsExactly(item.toString());
		window.label("errorMessageLabel")
			.requireText("No existing item with id 1: " + item);
	}
	
	@Test @GUITest
	public void testUpdateButtonSuccess() {
	    // Step 1: Add an item to the repository so we have something to update
		Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		itemRepository.save(item);

	    GuiActionRunner.execute(() -> {
	    	inventorySwingView.getListItemModel().addElement(item);
	    });

	    window.list().selectItem(0);

	    window.textBox("idTextBox").requireText("1");
	    window.textBox("idTextBox").requireDisabled();

	    window.textBox("nameTextBox").setText("Updated Laptop");
	    window.textBox("quantityTextBox").setText("15");
	    window.textBox("priceTextBox").setText("799.99");
	    window.textBox("descriptionTextBox").setText("Updated Gaming Laptop");

	    window.button(JButtonMatcher.withText("Update Selected")).click();

	    assertThat(window.list().contents())
	        .containsExactly(new Item("1", "Updated Laptop", 15, 799.99, "Updated Gaming Laptop").toString());

	}



}
