package com.example.ecommerce_a.domain;

public class Review {
	// id
	private Integer id;
	// ニックネーム
	private String name;
	// 施設レビュー
	private String review;
	// saunasテーブルと紐づけるためのid
	private Integer saunasId;
	// 削除フラグ
	private boolean deleted;
	
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
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	@Override
	public String toString() {
		return "Review [id=" + id + ", name=" + name + ", review=" + review + ", saunasId=" + saunasId + ", deleted="
				+ deleted + "]";
	}
	
	
	
}
