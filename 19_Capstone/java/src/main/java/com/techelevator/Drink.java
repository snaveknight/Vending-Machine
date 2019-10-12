package com.techelevator;

import java.math.BigDecimal;

public class Drink extends ProductAbstract {

	public Drink(String type, String name, String code, BigDecimal cost, Integer quantity) {
		super(type, name, code, cost, quantity);
		// TODO Auto-generated constructor stub
	}
	private String dispenseMessage = "Glug Glug, Yum!";
	public String getDispenseMessage() {
		return (dispenseMessage);
	}
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return super.getType();
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
		super.setType(type);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		super.setName(name);
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return super.getCode();
	}

	@Override
	public void setCode(String code) {
		// TODO Auto-generated method stub
		super.setCode(code);
	}

	@Override
	public BigDecimal getCost() {
		// TODO Auto-generated method stub
		return super.getCost();
	}

	@Override
	public void setCost(BigDecimal cost) {
		// TODO Auto-generated method stub
		super.setCost(cost);
	}

	@Override
	public Integer getQuantity() {
		// TODO Auto-generated method stub
		return super.getQuantity();
	}

	@Override
	public void setQuantity(Integer quantity) {
		// TODO Auto-generated method stub
		super.setQuantity(quantity);
	}
	

}
