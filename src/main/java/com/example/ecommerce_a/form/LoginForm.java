package com.example.ecommerce_a.form;

public class LoginForm {
	// メールアドレス
	private String email;
	// パスワード
	private String password;
	
	// 下記にてgetter/setterを定義
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
	
	@Override
	public String toString() {
		return "LoginForm [email=" + email + ", password=" + password + "]";
	}
}
