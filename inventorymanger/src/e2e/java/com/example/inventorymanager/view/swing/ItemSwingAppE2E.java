package com.example.inventorymanager.view.swing;

import static org.junit.Assert.*;

import javax.swing.JFrame;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.launcher.ApplicationLauncher.*;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.bson.Document;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MongoDBContainer;

import com.mongodb.MongoClient;

@RunWith(GUITestRunner.class)
public class ItemSwingAppE2E extends AssertJSwingJUnitTestCase {

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");

	private static final String DB_NAME = "test-db";
	private static final String COLLECTION_NAME = "test-collection";

	private MongoClient mongoClient;

	private FrameFixture window;

	@Override
	protected void onSetUp() throws Exception {
		// TODO Auto-generated method stub
		String containerIpAddress = mongo.getContainerIpAddress();
		Integer mappedPort = mongo.getFirstMappedPort();
		mongoClient = new MongoClient(containerIpAddress, mappedPort);
		// always start with an empty database
		mongoClient.getDatabase(DB_NAME).drop();
		// add some students to the database
		addTestItemToDatabase("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		addTestItemToDatabase("2", "Laptop", 1, 99.99, "gaming laptop");
		// start the Swing application
		application("com.example.inventorymanager.app.swing.InventorySwingApp")
				.withArgs("--mongo-host=" + containerIpAddress, "--mongo-port=" + mappedPort.toString(),
						"--db-name=" + DB_NAME, "--db-collection=" + COLLECTION_NAME)
				.start();
		// get a reference of its JFrame
		window = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
			@Override
			protected boolean isMatching(JFrame frame) {
				return "Inventory Manager".equals(frame.getTitle()) && frame.isShowing();
			}
		}).using(robot());
	}
	
	@Override
	protected void onTearDown() {
		mongoClient.close();
	}

	@Test @GUITest
	public void testOnStartAllDatabaseElementsAreShown() {
		assertThat(window.list().contents())
	    .anySatisfy(e -> assertThat(e).contains("1", "Laptop", "10", "999.99", "High-end gaming laptop"))
	    .anySatisfy(e -> assertThat(e).contains("2", "Laptop", "1", "99.99", "gaming laptop"));
	}


	private void addTestItemToDatabase(String id, String name, int quantity, double price, String description) {
		mongoClient.getDatabase(DB_NAME).getCollection(COLLECTION_NAME).insertOne(new Document()

				.append("id", id).append("name", name).append("quantity", quantity).append("price", price)
				.append("description", description));
	}
}
