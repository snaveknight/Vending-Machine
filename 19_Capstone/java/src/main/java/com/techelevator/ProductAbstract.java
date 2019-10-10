package com.techelevator;

public abstract class ProductAbstract {
	private String type;
	private String name;
	private String code;
	private Double cost;
	private Integer quantity;

	public ProductAbstract(String type, String name, String code, Double cost, Integer quantity) {
		this.type = type;
		this.name = name;
		this.code = code;
		this.cost = cost;
		this.quantity = quantity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	
}
