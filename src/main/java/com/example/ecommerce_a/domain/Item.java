package com.example.ecommerce_a.domain;

/**
 * アイテムを表すドメインクラスです.<br>
 * 
 * @author cyjoh
 *
 */
public class Item {
	/** ID */
	private Integer id;

	/** アイテム名 */
	private String name;

	/** 説明 */
	private String description;

	/** Sサイズの価格 */
	private Integer priceS;

	/** Mサイズの価格 */
	private Integer priceM;

	/** Lサイズの価格 */
	private Integer priceL;

	/** 画像パス */
	private String imagePath;

	/** 削除フラグ */
	private boolean deleted;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPriceS() {
		return priceS;
	}

	public void setPriceS(Integer priceS) {
		this.priceS = priceS;
	}

	public Integer getPriceM() {
		return priceM;
	}

	public void setPriceM(Integer priceM) {
		this.priceM = priceM;
	}

	public Integer getPriceL() {
		return priceL;
	}

	public void setPriceL(Integer priceL) {
		this.priceL = priceL;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", priceS=" + priceS + ", priceM="
				+ priceM + ", priceL=" + priceL + ", imagePath=" + imagePath + ", deleted=" + deleted + "]";
	}

}
