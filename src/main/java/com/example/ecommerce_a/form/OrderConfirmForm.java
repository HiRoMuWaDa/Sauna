package com.example.ecommerce_a.form;

import java.sql.Date;
import java.time.LocalTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * order_confirm.htmlから値を受け取るためのフォーム
 * 
 * @author manami
 *
 */
public class OrderConfirmForm {
	// 宛先氏名
	@NotBlank(message = "名前を入力してください")
	private String destinationName;
	// 宛先Eメール
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式が不正です")
	private String destinationEmail;

	// 宛先郵便番号
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "郵便番号は「xxx-xxxx」の形式で入力してください")
	private String destinationZipcode;

	// 宛先住所
	@NotBlank(message = "住所を入力してください")
	private String destinationAddress;
	// 宛先TEL
	@Pattern(regexp = "^0\\d{2,3}-\\d{1,4}-\\d{4}$", message = "電話番号は「xxxx-xxxx-xxxx」の形式で入力してください")
	private String destinationTel;

//	// 配達時間
//	private Timestamp deliveryTime;

	// 配達日
	@NotBlank(message = "配達日を入力してください")
	private String deliveryDate;

	// 配達時間
	@NotBlank(message = "配達時間を入力してください")
	private String deliveryTime;

	// 支払方法（1.代引 2.クレジットカード）
	@NotNull(message = "支払い方法を選択してください")
	private Integer paymentMethod;

	// 使用ポイント数
	@NotBlank(message = "使用するポイント数を選んでください")
	private String usePoint;

	public Integer getUsePointAsInteger() {
		return Integer.parseInt(usePoint);
	}

	public Date getDeliveryDateAsDate() {
		return Date.valueOf(deliveryDate);
	}

	public LocalTime getDeliveryTimeAsLocalTime() {
		String[] dateAndTime = deliveryTime.split(":");
		LocalTime deliveryTimeAsLocalTime = LocalTime.of(Integer.parseInt(dateAndTime[0]),
				Integer.parseInt(dateAndTime[1]));
		return deliveryTimeAsLocalTime;
	}

	// getter/setterを定義
	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

//	public Timestamp getDeliveryTime() {
//		return deliveryTime;
//	}
//
//	public void setDeliveryTime(Timestamp deliveryTime) {
//		this.deliveryTime = deliveryTime;
//	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getUsePoint() {
		return usePoint;
	}

	public void setUsePoint(String usePoint) {
		this.usePoint = usePoint;
	}

	@Override
	public String toString() {
		return "OrderConfirmForm [destinationName=" + destinationName + ", destinationEmail=" + destinationEmail
				+ ", destinationZipcode=" + destinationZipcode + ", destinationAddress=" + destinationAddress
				+ ", destinationTel=" + destinationTel + ", deliveryDate=" + deliveryDate + ", deliveryTime="
				+ deliveryTime + ", paymentMethod=" + paymentMethod + "]";
	}

}
