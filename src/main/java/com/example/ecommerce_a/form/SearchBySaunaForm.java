package com.example.ecommerce_a.form;

import java.util.List;

public class SearchBySaunaForm {
	// 施設名
	private String searchingName;
	// 男湯のサウナ室の温度
	private String searchingMaleSaunaRoomTemp;
	// 男湯の水風呂の温度
	private String searchingMaleWaterBath;
	// 女湯のサウナ室の温度
	private String searchingFemaleSaunaRoomTemp;
	// 女湯の水風呂の温度
	private String searchingFemaleWaterBath;
	// エリア
	private List<Integer> searchingArea;
	
	// String型をint型に変換
	public int getIntSearchingMaleSaunaRoomTemp() {
		return Integer.parseInt(getSearchingMaleSaunaRoomTemp());
	}
	public int getIntSearchingMaleWaterBath() {
		return Integer.parseInt(getSearchingMaleWaterBath());
	}
	public int getIntSearchingFemaleSaunaRoomTemp() {
		return Integer.parseInt(getSearchingFemaleSaunaRoomTemp());
	}
	public int getIntSearchingMefaleWaterBath() {
		return Integer.parseInt(getSearchingFemaleWaterBath());
	}
	
	// getter/setterを定義
	public String getSearchingName() {
		return searchingName;
	}
	public void setSearchingName(String searchingName) {
		this.searchingName = searchingName;
	}
	public String getSearchingMaleSaunaRoomTemp() {
		return searchingMaleSaunaRoomTemp;
	}
	public void setSearchingMaleSaunaRoomTemp(String searchingMaleSaunaRoomTemp) {
		this.searchingMaleSaunaRoomTemp = searchingMaleSaunaRoomTemp;
	}
	public String getSearchingMaleWaterBath() {
		return searchingMaleWaterBath;
	}
	public void setSearchingMaleWaterBath(String searchingMaleWaterBath) {
		this.searchingMaleWaterBath = searchingMaleWaterBath;
	}
	public String getSearchingFemaleSaunaRoomTemp() {
		return searchingFemaleSaunaRoomTemp;
	}
	public void setSearchingFemaleSaunaRoomTemp(String searchingFemaleSaunaRoomTemp) {
		this.searchingFemaleSaunaRoomTemp = searchingFemaleSaunaRoomTemp;
	}
	public String getSearchingFemaleWaterBath() {
		return searchingFemaleWaterBath;
	}
	public void setSearchingFemaleWaterBath(String searchingFemaleWaterBath) {
		this.searchingFemaleWaterBath = searchingFemaleWaterBath;
	}
	public List<Integer> getSearchingArea() {
		return searchingArea;
	}
	public void setSearchingArea(List<Integer> searchingArea) {
		this.searchingArea = searchingArea;
	}
	
	@Override
	public String toString() {
		return "SearchBySaunaForm [searchingName=" + searchingName + ", searchingMaleSaunaRoomTemp="
				+ searchingMaleSaunaRoomTemp + ", searchingMaleWaterBath=" + searchingMaleWaterBath
				+ ", searchingFemaleSaunaRoomTemp=" + searchingFemaleSaunaRoomTemp + ", searchingFemaleWaterBath="
				+ searchingFemaleWaterBath + ", searchingArea=" + searchingArea + "]";
	}	
}
