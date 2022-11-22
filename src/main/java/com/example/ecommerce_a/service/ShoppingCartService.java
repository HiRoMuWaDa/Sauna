package com.example.ecommerce_a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Option;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderOption;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.repository.ItemRepository;
import com.example.ecommerce_a.repository.OptionRepository;
import com.example.ecommerce_a.repository.OrderItemRepository;
import com.example.ecommerce_a.repository.OrderOptionRepository;
import com.example.ecommerce_a.repository.OrderRepository;

@Service
@Transactional
public class ShoppingCartService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderOptionRepository orderOptionRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private OptionRepository optionRepository;
	
	/**
	 * 現在ログインしているユーザー情報に紐づく、注文前ステータスのorderドメインを取得するためのメソッド.<br>
	 * 
	 * @param user　現在ログインしているユーザー情報。
	 * @return そのユーザーの注文前オーダー(=ショッピングカート)が存在するかどうか。trueの場合は存在する。falseの場合は存在しない。
	 */
	public Order getShoppingCartOf(User user) {
		List<Order> shoppingCartAsList = orderRepository.findByUserIdAndStatus(user.getId(), 0);
		if(shoppingCartAsList.size() == 0) {
			return null;
		} else {
			return shoppingCartAsList.get(0) ;
		}
	}
	
	public void deleteItemOf(Integer orderItemId) {
		orderItemRepository.delete(orderItemId);
		
	}
	
//	public void deleteItemOf(Integer orderItemId,Order order) {
//		orderItemRepository.delete(orderItemId);
//		int totalPrice = order.getCalcTotalPrice()- orderItemId;
//		orderRepository.updateTotalPrice(order.getId(),);		
//	}
//	
//	
	public void updateTotalPriceOf(Order order) {
		orderRepository.updateTotalPrice(order.getId(),order.getCalcTotalPrice());
	}

	public int createNewShoppingCart(Order order) {
		return orderRepository.insert(order);
	}
	
	
	public Item getItemInfo(Integer id) {
		return itemRepository.findById(id);
	}
	
	public Option getOptionInfo(Integer id) {
		return optionRepository.load(id);
	}
	
	
	public int insertOrderItem(OrderItem orderItem) {
		return orderItemRepository.insert(orderItem);
	}
	
	public void insertOrderOption(OrderOption orderOption) {
		orderOptionRepository.insert(orderOption);
	}
	
}

