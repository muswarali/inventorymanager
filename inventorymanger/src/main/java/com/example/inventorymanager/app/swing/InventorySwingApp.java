package com.example.inventorymanager.app.swing;

import java.awt.EventQueue;

import com.example.inventorymanager.repository.mongo.ItemMongoRepository;
import com.example.inventorymanager.view.swing.InventorySwingView;
import com.example.inventorymanger.controller.ItemController;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class InventorySwingApp {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {

			try {
				String mongoHost = "localhost";
				int mongoPort = 27017;
				if (args.length > 0)
					mongoHost = args[0];
				if (args.length > 1)
					mongoPort = Integer.parseInt(args[1]);
				ItemMongoRepository itemRepository = new ItemMongoRepository(
						new MongoClient(new ServerAddress(mongoHost, mongoPort)), "inventory", "student");
				InventorySwingView itemView = new InventorySwingView();
				ItemController itemController = new ItemController(itemRepository, itemView);
				itemView.setItemController(itemController);
				itemView.setVisible(true);
				itemController.getAllItems();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
