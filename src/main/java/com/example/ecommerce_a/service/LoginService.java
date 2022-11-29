package com.example.ecommerce_a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.repository.UserRepository;

@Service
public class LoginService {
	@Autowired
	private UserRepository userRepository;
	
	// パスワードのハッシュ化に利用するクラス
		@Autowired
		PasswordEncoder passwordEncoder;
	
	/**
	 * メールアドレスとパスワードで検索するメソッド。
	 * @param user
	 * @return　
	 */
//	public User login(String email, String password) {
//		String hashedPassword = passwordEncoder.encode(password);
//		System.out.println("hashedPassword:"+hashedPassword);
//		User user = userRepository.findByEmailAndPassword(email, hashedPassword);
//		return user;
//	}
	public User login(String email, String password) {
		List<User> user = userRepository.findByMailAddress(email);
		if(user == null || user.size()==0) {
			return null;
		}
		if (!passwordEncoder.matches(password, user.get(0).getPassword())) {
			return null;
		}
		System.out.println(111);
		return user.get(0);
	}
}
