package com.example.inventorymanger.repository;

import com.example.inventorymanger.model.Item;

import java.util.List;

public interface ItemRepository {
	   void save(Item item);
	   List<Item> findAll();
	   Item findById(String id);
	   void update(Item item);
	   void delete(String id);

}
