package com.app.feeds;

import java.io.Serializable;

public class FeedMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private double price;
	private int shares;
	
	public FeedMessage(String name, int shares, double price) {
		this.name = name;
		this.shares = shares;
		this.price = price;
		
		//System.out.println(name+"|"+shares+"|"+price);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getShares() {
		return shares;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}
	
}