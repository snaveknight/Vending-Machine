package com.techelevator;

public class Beverage {
	private String name = "sodaOne" ;
	private Double price = 0.0;
	private int quantity = 5;
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		// TODO Auto-generated method stub
		return price;
	}

	public Beverage(String name, Double price, int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

}
