package com.example.ecommerce_a.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;

import com.example.ecommerce_a.controller.ShoppingCartController;
import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderOption;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.OrderConfirmForm;

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
		OrderConfirmForm form =new OrderConfirmForm();
		form.setUsePoint("50");
		user.setId(1);
		user.setName("テストユーザ");
		user.setEmail("coffeeshop.test@gmail.com");
		user.setPassword("abc");
		user.setAddress("テスト住所");
		user.setZipcode("1111111");
		user.setTelephone("テスト電話番号");
		sessionMap.put("userId", user.getId());
		sessionMap.put("user", user);

		Order order = new Order();
		List<OrderItem> orderItemList = new ArrayList<>();
//		order.setTotalPrice(10000);
		order.setOrderItemList(orderItemList);	
		//orderにorderListをセット
		OrderItem orderItem = new OrderItem();
		Item item = new Item();
		item.setId(1);
		item.setName("サウナタオル白");
		item.setPriceM(1000);
		orderItem.setItemId(1);
		orderItem.setQuantity(5);
		orderItem.setSize('M');
		orderItem.setItem(item);
		orderItemList.add(orderItem);
		List<OrderOption> orderOptionList = new ArrayList<>();
		orderItem.setOrderOptionList(orderOptionList);
		sessionMap.put("shoppingCart", order);
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
