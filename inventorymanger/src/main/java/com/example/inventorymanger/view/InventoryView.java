package com.example.inventorymanger.view;

import java.util.List;

import com.example.inventorymanger.model.Item;

public interface InventoryView {
	 	void displayItems(List<Item> items);
	    void showItemDetails(Item item);
	    void showAddItemSuccess();
	    void showDeleteItemSuccess();
	    void showErrorMessage(String message);
}
