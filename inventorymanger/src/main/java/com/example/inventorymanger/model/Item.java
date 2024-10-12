package com.example.inventorymanger.model;

public class Item {
    private String id;
    private String name;
    private int quantity;
    private double price;
    private String description;

    public Item(String id, String name, int quantity, double price, String description) {
        this.setId(id);
        this.setName(name);
        this.setQuantity(quantity);
        this.setPrice(price);
        this.setDescription(description);
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
