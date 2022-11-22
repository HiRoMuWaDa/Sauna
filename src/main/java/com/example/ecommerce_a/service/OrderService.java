package com.example.ecommerce_a.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.repository.OrderRepository;

/**
 * 注文確定後、注文確定メールの送信とクレジットカード決済処理を行う
 * @author manami
 *
 */
@Service
@Transactional
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	public Order completeOrder(Order order) {
		order = orderRepository.save(order);
		return order;
	}
	
	public List<Order> showOrderHistoryOf(User user){
		return orderRepository.findOrderHistory(user.getId());
	}
}
