package com.example.ecommerce_a.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.ecommerce_a.controller.ShoppingCartController;
import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderOption;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.OrderForm;
import com.example.ecommerce_a.repository.OrderItemRepository;
import com.example.ecommerce_a.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;

public class SessionUtil {
	@Autowired
	ShoppingCartController shoppingcontroller;
	@Autowired

	public static MockHttpSession createUserIdAndUserSession() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		User user = new User();
		user.setId(1);
		user.setName("テストユーザ");
		user.setEmail("coffeeshop.test@gmail.com");
		user.setPassword("abc");
		user.setAddress("テスト住所");
		user.setZipcode("1111111");
		user.setTelephone("テスト電話番号");
		sessionMap.put("userId", user.getId());
		sessionMap.put("user", user);
		return createMockHttpSession(sessionMap);
	}

//	private static MockHttpSession createMockHttpSession(Map<String, Object> sessions) {
//		MockHttpSession mockHttpSession = new MockHttpSession();
//		for (Map.Entry<String, Object> session : sessions.entrySet()) {
//			mockHttpSession.setAttribute(session.getKey(), session.getValue());
//		}
//		return mockHttpSession;

	public static MockHttpSession createShoppingCartIdAｄItemSession() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		User user = new User();
		user.setId(1);
		user.setName("テストユーザ");
		user.setEmail("coffeeshop.test@gmail.com");
		user.setPassword("abc");
		user.setAddress("テスト住所");
		user.setZipcode("1111111");
		user.setTelephone("テスト電話番号");
		sessionMap.put("userId", user.getId());
		
		
		sessionMap.put("user", user);
		Order order=new Order();
		OrderItem orderitem=new OrderItem();
		Item item=new Item();
		List<OrderItem> orderItemList=new ArrayList<>();
		
		item.setId(0);
		item.setName(null);
		orderitem.setId(0);
		orderitem.setItem(null);
		orderitem.setItemId(0);
		orderitem.setOrderId(0);
		orderitem.setSize('M');
		orderitem.setQuantity(0);
		orderItemList.set(orderitem.getId(), orderitem);
		orderItemList.set(orderitem.getItemId(), orderitem);
		orderItemList.set(orderitem.getOrderId(), orderitem);
		orderItemList.set(orderitem.getSize(), orderitem);
		orderItemList.set(orderitem.getQuantity(), orderitem);
		
		
		
		
		
		sessionMap.put("orderItemList", orderItemList.get(0));
//	
		sessionMap.put("orderItemList", orderItemList);
	
		
		

	
		return createMockHttpSession(sessionMap);
	}
	private static MockHttpSession createMockHttpSession(Map<String, Object> sessions) {
		MockHttpSession mockHttpSession = new MockHttpSession();
		for (Map.Entry<String, Object> session : sessions.entrySet()) {
			mockHttpSession.setAttribute(session.getKey(), session.getValue());
		}
		return mockHttpSession;
	}
}
