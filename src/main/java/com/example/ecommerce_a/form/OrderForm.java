package com.example.ecommerce_a.form;

import java.util.List;

public class OrderForm {
	private Integer itemId;
	private Integer quantity;
	private Character size;
	private List<Integer> optionIdList;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public List<Integer> getOptionIdList() {
		return optionIdList;
	}

	public void setOptionIdList(List<Integer> optionIdList) {
		this.optionIdList = optionIdList;
	}

	@Override
	public String toString() {
		return "OrderForm [itemId=" + itemId + ", quantity=" + quantity + ", size=" + size + ", optionIdList="
				+ optionIdList + "]";
	}

}
