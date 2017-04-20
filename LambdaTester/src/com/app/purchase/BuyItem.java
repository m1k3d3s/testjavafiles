package com.app.purchase;

public class BuyItem {
	
	private String description;
	private double price;
	private int sku;
	private int id;
	private static int numOfItemsBought = 0;
	
	
	public BuyItem(String d, double p, int s) {
		description = d;
		price = p;
		sku = s;
		
		id = ++numOfItemsBought;
	}
	
	public int getID() {
		return id;
	}

	public String parseItemMembers(BuyItem b) {
		b.id = this.id;
		b.price = this.price;
		b.sku = this.sku;
		
		String receipt = b.id + " " + b.price + " " + b.sku;
		
		return receipt;
	}
}
