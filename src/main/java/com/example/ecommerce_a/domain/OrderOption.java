package com.example.ecommerce_a.domain;

/**
 * 注文オプション用のドメイン
 * toppingをoptionに変更しています
 * @author manami
 *
 */
public class OrderOption {
	// id
	private Integer id;
	// オプションid
	private Integer optionId;
	// 注文詳細id
	private Integer orderItemId;
	// オプション
	private Option option;
	
	// getter/setterを定義
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOptionId() {
		return optionId;
	}
	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}
	public Integer getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}
	public Option getOption() {
		return option;
	}
	public void setOption(Option option) {
		this.option = option;
	}

	@Override
	public String toString() {
		return "OrderOption [id=" + id + ", optionId=" + optionId + ", orderItemId=" + orderItemId + "]";
	}
}
