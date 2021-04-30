package com.arocruz8.spring.drools.api;

public class Order {

	private String name;
	private String cardType;
	private float discount;
	private int price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Order [name=" + name + ", cardType=" + cardType + ", discount= %" + discount + ", price=" + price + "]";
	}


}
