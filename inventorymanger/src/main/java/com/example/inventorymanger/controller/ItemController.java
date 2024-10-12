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

}
