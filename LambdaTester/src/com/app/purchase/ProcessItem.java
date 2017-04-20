package com.app.purchase;

public class ProcessItem {
	
	public static void main(String[] args) {
		
		BuyItem buy_it = new BuyItem("Toy", 19.99, 343234);
		BuyItem buy_it1 = new BuyItem("Toy", 19.99, 343234);
		BuyItem buy_it2 = new BuyItem("Toy", 19.99, 343234);
		BuyItem buy_it3 = new BuyItem("Toy", 19.99, 343234);
		BuyItem buy_it4 = new BuyItem("Toy", 19.99, 343234);
		BuyItem buy_it5 = new BuyItem("Toy", 19.99, 343234);
		BuyItem buy_it6 = new BuyItem("Toy", 19.99, 343234);

		
		System.out.println(buy_it6.getID());
	}
	
}


