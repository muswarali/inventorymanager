package com.example.inventorymanager.app.swing;

import java.awt.EventQueue;
import java.util.concurrent.Callable;

import com.example.inventorymanager.repository.mongo.ItemMongoRepository;
import com.example.inventorymanager.view.swing.InventorySwingView;
import com.example.inventorymanger.controller.ItemController;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;


@Command(mixinStandardHelpOptions = true)
public class InventorySwingApp implements Callable<Void>{
	
	@Option(names = { "--mongo-host" }, description = "MongoDB host address")
	private String mongoHost = "localhost";

	@Option(names = { "--mongo-port" }, description = "MongoDB host port")
	private int mongoPort = 27017;

	@Option(names = { "--db-name" }, description = "Database name")
	private String databaseName = "inventory";

	@Option(names = { "--db-collection" }, description = "Collection name")
	private String collectionName = "item";
	
	
	public static void main(String[] args) {
		new CommandLine(new InventorySwingApp()).execute(args);
	}

	
	@Override
	public Void call() throws Exception  {
		
		EventQueue.invokeLater(() -> {

			try {
			
				ItemMongoRepository itemRepository = new ItemMongoRepository(
						new MongoClient(new ServerAddress(mongoHost, mongoPort)), databaseName, collectionName);
				InventorySwingView itemView = new InventorySwingView();
				ItemController itemController = new ItemController(itemRepository, itemView);
				itemView.setItemController(itemController);
				itemView.setVisible(true);
				itemController.getAllItems();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return null;
	}

}
