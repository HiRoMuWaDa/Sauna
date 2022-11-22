package com.example.ecommerce_a.domain;

import java.util.List;

/**
 * sauna_listテーブル用のドメインクラス
 * 
 * @author manami
 *
 */
public class Sauna {
	// id
	private Integer id;
	// 施設名
	private String name;
	// サウナ施設のある地域（東京23区内）
	private String area;
	// 価格
	private Integer price;
	// 男湯側のサウナ室の温度
	private Integer maleSaunaRoomTemp;
	// 男湯側の水風呂の温度
	private Integer maleWaterBath;
	// 女湯側のサウナ室の温度
	private Integer femaleSaunaRoomTemp;
	// 女湯側の水風呂の温度
	private Integer femaleWaterBath;
	// 施設の説明
	private String description;
	// 写真のパス
	private String imagePath;
	// 施設URL
	private String url;
	// 施設レビュー
	private List<Review> reviewList;
	// 削除フラグ
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getMaleSaunaRoomTemp() {
		return maleSaunaRoomTemp;
	}

	public void setMaleSaunaRoomTemp(Integer maleSaunaRoomTemp) {
		this.maleSaunaRoomTemp = maleSaunaRoomTemp;
	}

	public Integer getMaleWaterBath() {
		return maleWaterBath;
	}

	public void setMaleWaterBath(Integer maleWaterBath) {
		this.maleWaterBath = maleWaterBath;
	}

	public Integer getFemaleSaunaRoomTemp() {
		return femaleSaunaRoomTemp;
	}

	public void setFemaleSaunaRoomTemp(Integer femaleSaunaRoomTemp) {
		this.femaleSaunaRoomTemp = femaleSaunaRoomTemp;
	}

	public Integer getFemaleWaterBath() {
		return femaleWaterBath;
	}

	public void setFemaleWaterBath(Integer femaleWaterBath) {
		this.femaleWaterBath = femaleWaterBath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Review> getReviewList() {
		return reviewList;
	}

	public void setReviewList(List<Review> reviewList) {
		this.reviewList = reviewList;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Sauna [id=" + id + ", name=" + name + ", area=" + area + ", price=" + price + ", maleSaunaRoomTemp="
				+ maleSaunaRoomTemp + ", maleWaterBath=" + maleWaterBath + ", femaleSaunaRoomTemp="
				+ femaleSaunaRoomTemp + ", femaleWaterBath=" + femaleWaterBath + ", description=" + description
				+ ", imagePath=" + imagePath + ", url=" + url + ", reviewList=" + reviewList + ", deleted=" + deleted
				+ "]";
	}

}
