package com.example.ecommerce_a.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.repository.OrderItemRepository;

@Service
@Transactional
public class OrderItemService {
	@Autowired
	private OrderItemRepository orderItemRepository;

	public List<OrderItem> orderConfirm(Integer orderId) {
		List<OrderItem> orderItemList = orderItemRepository.findAllOrderItem(orderId);
		return orderItemList;
	}
	//List<OrderItem>→注文されたアイテム（商品id、数量、サイズ、オプションリストなど）
	//	List<OrderItem> orderItemList = orderItemRepository.findAllOrderItem(orderId);
	//→商品をsqlから検索してきてリストに入れる
		
}
