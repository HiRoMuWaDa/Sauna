package com.example.ecommerce_a.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public Integer getPoint(Integer id) {
		Integer getPoint = userRepository.load(id).getPoint();
		return getPoint;
	}
	
	/**
	 * SaunasRepositoryのupdatePointを実行するメソッド
	 * @param user
	 * @return user
	 */
	public User updatePoint(User user,Integer point) {
		Integer nowPoint = userRepository.load(user.getId()).getPoint();
		nowPoint += point ;
		user.setPoint(nowPoint);
		return userRepository.updatePoint(user);

	}

}
