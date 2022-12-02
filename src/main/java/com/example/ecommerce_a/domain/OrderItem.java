package com.example.ecommerce_a.domain;

import java.util.List;

/**
 * 注文商品用のドメイン
 * toppingをoptionに設定
 * 小計金額を計算する
 * @author manami
 *一つの注文内容
 */
public class OrderItem {
	// id
	private Integer id;
	// アイテムid
	private Integer itemId;
	// オーダーid
	private Integer orderId;
	// 数量
	private Integer quantity;
	// サイズ
	private Character size;
	// 商品
	private Item item;
	// 注文オプションリスト
	private List<OrderOption> orderOptionList;
	
	
	/**
	 * 小計金額を表示するためのメソッドを定義
	 * @return (サイズごとの価格 + オプションの価格) * 数量
	 */
	public int getSubTotal() {
		int optionTotalPrice = 0;
		for (OrderOption orderOption : orderOptionList) {
			optionTotalPrice += orderOption.getOption().getPrice();
		}
		
		if (size == 'S') {
			return (item.getPriceS() + optionTotalPrice) * quantity;
		} else if (size == 'M') {
			return (item.getPriceM() + optionTotalPrice) * quantity;
		} else if (size == 'L') {
			return (item.getPriceL() + optionTotalPrice) * quantity;
		} else {
			return 0;
		}
	}
	
	// getter/setterを定義
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public List<OrderOption> getOrderOptionList() {
		return orderOptionList;
	}

	public void setOrderOptionList(List<OrderOption> orderOptionList) {
		this.orderOptionList = orderOptionList;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", item=" + item + ", orderOptionList=" + orderOptionList + "]";
	} 

}
