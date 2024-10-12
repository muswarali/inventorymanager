package com.example.inventorymanger.controller;

import com.example.inventorymanger.model.Item;
import com.example.inventorymanger.repository.ItemRepository;

import java.util.List;

public class ItemController {

	private ItemRepository itemRepository;
	
	public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
	
	public void addItem(Item item) {
	     itemRepository.save(item);
	}
	public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(String id) {
        return itemRepository.findById(id);
    }

    public void updateItem(Item item) {
        itemRepository.update(item);
    }

    public void deleteItem(String id) {
        itemRepository.delete(id);
    }

}
