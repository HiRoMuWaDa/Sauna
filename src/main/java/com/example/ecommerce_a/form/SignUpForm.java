package com.example.ecommerce_a.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 会員登録時のデータを受け取るフォームクラスです.
 * 
 * @author cyjoh
 *
 */
public class SignUpForm {
	/** ユーザー名 */
	@NotBlank(message="名前は必ず入力してください。")
	private String name;

	/** メールアドレス */
	@NotBlank(message = "メールアドレスは必ず入力してください。")
	@Email(message = "メールアドレスの形式が不正です。")
	private String email;

	/** パスワード */
	@Pattern(regexp = "^([a-zA-Z0-9]{7,})$", message = "パスワードは8文字以上の英数字で入力してください。")
	private String password;

	/** 郵便番号 */
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "郵便番号はハイフンありの形式で入力してください。")
	private String zipcode;

	/** 住所 */
	@NotBlank(message="住所は必ず入力してください。")
	private String address;

	/** 電話番号 */
	@Pattern(regexp = "^0[-0-9]{11,12}$", message = "電話番号はハイフンありの形式で入力してください。")
	private String telephone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return "SignUpForm [name=" + name + ", email=" + email + ", password=" + password + ", zipcode=" + zipcode
				+ ", address=" + address + ", telephone=" + telephone + "]";
	}

}
