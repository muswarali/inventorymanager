package com.example.inventorymanger.view;

import java.util.List;

import com.example.inventorymanger.model.Item;

public interface InventoryView {
	 	void displayItems(List<Item> items);
	    void addItem(Item item);
	    void deleteItem(Item item);
	    void updateItem(Item item);
	    void showErrorMessage(String message, Item item);
}
