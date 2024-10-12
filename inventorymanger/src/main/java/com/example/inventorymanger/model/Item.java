package com.example.inventorymanger.model;

public class Item {
    private String id;
    private String name;
    private int quantity;

    public Item(String id, String name, int quantity) {
        this.setId(id);
        this.setName(name);
        this.setQuantity(quantity);
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
