package com.example.ecommerce_a.domain;

public class Option {
	private Integer id;
	private String name;
	private Integer price;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Option [id=" + id + ", name=" + name + ", price=" + price + "]";
	}

}
