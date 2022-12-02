package com.example.ecommerce_a.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.mock.web.MockHttpSession;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Option;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderOption;

import java.util.LinkedHashMap;
import java.util.Map;

import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.OrderConfirmForm;

import org.springframework.mock.web.MockHttpSession;

public class SessionUtil {

	public static MockHttpSession createUserIdAndUserSession() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		User user = new User();
		user.setId(1);
		user.setName("テストユーザ");
		user.setPassword("abc");
		user.setEmail("test@test.co.jp");
		user.setAddress("テスト住所");
		user.setZipcode("1111111");
		user.setTelephone("テスト電話番号");
		sessionMap.put("userId", user.getId());
		sessionMap.put("user", user);
		return createMockHttpSession(sessionMap);
	}

	private static MockHttpSession createMockHttpSession(Map<String, Object> sessions) {

		MockHttpSession mockHttpSession = new MockHttpSession();
		for (Map.Entry<String, Object> session : sessions.entrySet()) {
			mockHttpSession.setAttribute(session.getKey(), session.getValue());
		}
		return mockHttpSession;
	}

	public static MockHttpSession createShoppingCartIdItemSession() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();

		Order order = new Order();
		List<OrderItem> orderItemList = new ArrayList<>();
//		order.setTotalPrice(10000);
		order.setOrderItemList(orderItemList);
		// orderにorderListをセット

//		商品情報
		OrderItem orderItem = new OrderItem();
		Item item = new Item();
		item.setId(1);
		item.setName("サウナタオル白");
		item.setPriceS(1000);
		orderItem.setItemId(1);
		orderItem.setQuantity(10);
		orderItem.setSize('S');
		orderItem.setItem(item);
		orderItemList.add(orderItem);
		OrderItem orderItem1 = new OrderItem();
		Item item = new Item();
		item.setId(1);
		item.setName("サウナタオル白");
		item.setPriceS(1000);
		orderItem.setItemId(1);
		orderItem.setQuantity(10);
		orderItem.setSize('S');
		orderItem.setItem(item);
		orderItemList.add(orderItem);


		List<OrderOption> orderOptionList = new ArrayList<>();
		orderItem.setOrderOptionList(orderOptionList);

//		オプション情報
		OrderOption orderOption = new OrderOption();
		orderOption.setId(1);
		orderOption.setOptionId(1);
		orderOption.setOrderItemId(1);
		orderOptionList.add(orderOption);

		sessionMap.put("shoppingCart", order);
		sessionMap.put("orderOption", order);
		sessionMap.put("beforeLogin", "orderconfirm");
		return createMockHttpSession(sessionMap);
	}

	public static MockHttpSession createShoppingCartIdItemSession2() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();

		Order order = new Order();
		List<OrderItem> orderItemList = new ArrayList<>();
//		order.setTotalPrice(10000);
		order.setOrderItemList(orderItemList);
		// orderにorderListをセット

//		商品情報
		OrderItem orderItem = new OrderItem();
		Item item = new Item();
		item.setId(1);
		item.setName("サウナタオル白");
		item.setPriceS(1000);
		orderItem.setItemId(1);
		orderItem.setQuantity(10);
		orderItem.setSize('S');
		orderItem.setItem(item);
		orderItemList.add(orderItem);

		List<OrderOption> orderOptionList = new ArrayList<>();
		orderItem.setOrderOptionList(orderOptionList);

//		オプション情報
		OrderOption orderOption = new OrderOption();
		orderOption.setId(1);
		orderOption.setOptionId(1);
		orderOption.setOrderItemId(1);
		orderOptionList.add(orderOption);

		sessionMap.put("shoppingCart", order);
		sessionMap.put("orderOption", order);
		sessionMap.put("beforeLogin", "sauna");
		return createMockHttpSession(sessionMap);
	}

	public static MockHttpSession createShoppingCartIdItemSessionXXX() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();

		Order order = new Order();
		List<OrderItem> orderItemList = new ArrayList<>();
		order.setOrderItemList(orderItemList);

		sessionMap.put("shoppingCart", order);
		return createMockHttpSession(sessionMap);
	}
	public static MockHttpSession createShoppingCartIdAdItemSession() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		User user = new User();
		OrderItem orderItem = new OrderItem();
		
		List<OrderOption> orderOptionList = new ArrayList<>();
		orderItem.setOrderOptionList(orderOptionList);

//		オプション情報
		OrderOption orderOption = new OrderOption();
		orderOption.setId(1);
		orderOption.setOptionId(1);
		orderOption.setOrderItemId(1);
		orderOptionList.add(orderOption);
		
//		OrderConfirmForm form =new OrderConfirmForm();
//		form.setUsePoint("50");
		
		
	
		user.setId(1);
		user.setName("テストユーザ");
		user.setEmail("coffeeshop.test@gmail.com");
		user.setPassword("abc");
		user.setAddress("テスト住所");
		user.setZipcode("1111111");
		user.setTelephone("テスト電話番号");
		//user.setPoint(150);
		sessionMap.put("userId", user.getId());
		sessionMap.put("user", user);

	
		List<OrderItem> orderItemList = new ArrayList<>();
		Order order = new Order();
//		order.setTotalPrice(10000);
		order.setOrderItemList(orderItemList);	
		Item item = new Item();
		item.setId(1);
		item.setName("サウナタオル白");
		item.setPriceM(1000);
		orderItem.setItemId(1);
		orderItem.setQuantity(5);
		orderItem.setSize('M');
		orderItem.setItem(item);
		orderItem.setOrderOptionList((List<OrderOption>) orderOption);
		orderItemList.add(orderItem);
		order.setDeliveryTime(null);		
		order.setDestinationAddress("テスト住所");
		order.setDestinationEmail("coffeeshop.test@gmail.com");
		order.setDestinationName("テスト太郎");
		order.setDestinationTel("テスト電話番号");
		order.setDestinationZipcode("1111111");
		order.setId(1);
		order.setOrderDate(null);
		order.setPaymentMethod(1);
		order.setStatus(1);
		order.setUserId(1);
		order.setUser(user);
		
		
		
		sessionMap.put("shoppingCart", order);
				
		return createMockHttpSession(sessionMap);
		
	}
	
	

}
