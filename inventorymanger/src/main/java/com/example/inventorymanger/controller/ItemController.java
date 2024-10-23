package com.example.inventorymanger.controller;

import com.example.inventorymanger.model.Item;
import com.example.inventorymanger.repository.ItemRepository;
import com.example.inventorymanger.view.InventoryView;

public class ItemController {

	private ItemRepository itemRepository;
	private InventoryView inventoryView;
	
	public ItemController(ItemRepository itemRepository, InventoryView inventoryView ) {
        this.itemRepository = itemRepository;
        this.inventoryView = inventoryView;
    }
	
	public synchronized void addItem(Item item) {
	     Item existingitem = itemRepository.findById(item.getId());
	     if (existingitem != null) {
	    	 inventoryView.showErrorMessage("Already existing item with id "+ item.getId(), existingitem);
	    	 return;
	    	 }
	     
	     itemRepository.save(item);
	     inventoryView.addItem(item);
	
	}
	public void getAllItems() {
        inventoryView.displayItems(itemRepository.findAll());
    }

//    public Item getItemById(String id) {
//         itemRepository.findById(id);
//    }

    public synchronized void updateItem(Item item) {
	     Item existingitem = itemRepository.findById(item.getId());
	     if (existingitem == null) {
	    	 inventoryView.showErrorMessage("No existing item with id "+ item.getId(), existingitem);
	    	 return;
	    	 }
	     
	     itemRepository.update(item);
	     inventoryView.updateItem(item);
	
    }

    public synchronized void deleteItem(Item item) {
 	     if (itemRepository.findById(item.getId()) == null) {
 	    	 inventoryView.showErrorMessage("No existing item with id "+ item.getId(), item);
 	    	 return;
 	    	 }
 	     
 	     itemRepository.delete(item.getId());
 	     inventoryView.deleteItem(item);
    }

}
