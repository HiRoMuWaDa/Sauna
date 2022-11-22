package com.example.ecommerce_a.form;

public class ReviewForm {
	// id
	private Integer id;
	// 投稿者のニックネーム
	private String name;
	// レビュー
	private String review;
	// サウナ施設のid
	private Integer saunasId;
	
	// getter/setterを定義
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
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public Integer getSaunasId() {
		return saunasId;
	}
	public void setSaunasId(Integer saunasId) {
		this.saunasId = saunasId;
	}
	
	@Override
	public String toString() {
		return "ReviewForm [id=" + id + ", name=" + name + ", review=" + review + ", saunasId=" + saunasId + "]";
	}
}
