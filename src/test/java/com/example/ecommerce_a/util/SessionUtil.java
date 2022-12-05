package com.example.ecommerce_a.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.mock.web.MockHttpSession;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderOption;
import com.example.ecommerce_a.domain.User;

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
	
	//OrderControllerで使用
	
	
	
	
	public static MockHttpSession createShoppingCartIdAdItemSession() throws ParseException {
	

		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();

		User user = new User();
		OrderItem orderItem = new OrderItem();
		
//		オプション情報
		List<OrderOption> orderOptionList = new ArrayList<>();
		orderItem.setOrderOptionList(orderOptionList);
		OrderOption orderOption = new OrderOption();
		orderOption.setId(1);
		orderOption.setOptionId(1);
		orderOption.setOrderItemId(1);



		//後で変換するので変数に入れる
		Integer i = Integer.valueOf(150);
		
		// ユーザー情報
		
		user.setId(1);
		user.setName("テストユーザ");
		user.setEmail("coffeeshop.test@gmail.com");
		user.setPassword("abc");
		user.setAddress("テスト住所");
		user.setZipcode("111-1111");
		user.setTelephone("000-0000-0000");
		 user.setPoint(i);
		sessionMap.put("userId", user.getId());
		sessionMap.put("user", user);
		

		// shoppingCartデータ
		List<OrderItem> orderItemList = new ArrayList<>();
		
	

		Order order = new Order();
		order.setDeliveryTime(null);
		order.setDestinationAddress("111-1111");
		order.setDestinationEmail("");
		
	

		
		Item item = new Item();
		item.setId(1);
		item.setName("サウナタオル白");
		item.setPriceM(1000);
		orderItem.setItemId(1);
		orderItem.setQuantity(5);
		orderItem.setSize('M');
		orderItem.setItem(item);
		orderItem.setOrderOptionList(orderOptionList);
		orderItemList.add(orderItem);
		order.setId(1);
		order.setOrderItemList(orderItemList);

		sessionMap.put("shoppingCart", order);
		sessionMap.put("orderItemList", orderItemList);
		return createMockHttpSession(sessionMap);
	}
		

	
	   
		


	public static MockHttpSession createOrder() throws ParseException {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		
		User user = new User();
		OrderItem orderItem = new OrderItem();
		


	//Date変換
	Integer i=150;
		
		// ユーザー情報
		
		user.setId(1);
		user.setName("テストユーザ");
		user.setEmail("coffeeshop.test@gmail.com");
		user.setPassword("abc");
		user.setAddress("テスト住所");
		user.setZipcode("111-1111");
		user.setTelephone("000-0000-0000");
		 user.setPoint(i);
		sessionMap.put("userId", user.getId());
		sessionMap.put("user", user);
		

		// shoppingCartデータ
		List<OrderItem> orderItemList = new ArrayList<>();
//		オプション情報
		List<OrderOption> orderOptionList = new ArrayList<>();
		orderItem.setOrderOptionList(orderOptionList);
		OrderOption orderOption = new OrderOption();
		orderOption.setId(1);
	orderOption.setOptionId(1);
		orderOption.setOrderItemId(1);

		
		
	

		Order order = new Order();

		
//		LocalDate nowdate = LocalDate.now();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:MM:SS");
//		String str = "2022-12-10 10:00:000";
//			Date date =sdf.parse(str);
		
		Date now = new Date();
		
		 
		Item item = new Item();
		item.setId(1);
		item.setName("サウナタオル白");
		item.setPriceM(1000);
		orderItem.setItemId(1);
		orderItem.setQuantity(5);
		orderItem.setSize('M');
		orderItem.setItem(item);
		orderItem.setOrderOptionList(orderOptionList);
		orderItemList.add(orderItem);
		order.setId(1);
		order.setUserId(1);
		order.setStatus(1);
		order.setTotalPrice(5500);
		order.setUser(user);
//	order.setOrderDate(now);

		order.setOrderItemList(orderItemList);

		sessionMap.put("order", order);
		sessionMap.put("UserId", order.getUserId());
		sessionMap.put("orderOption",orderOption);
		
		sessionMap.put("orderItemList", orderItemList);
	return createMockHttpSession(sessionMap);}
	private static MockHttpSession createMockHttpSession1(Map<String, Object> sessions) {

		MockHttpSession mockHttpSession = new MockHttpSession();
		for (Map.Entry<String, Object> session : sessions.entrySet()) {
			mockHttpSession.setAttribute(session.getKey(), session.getValue());
		}
		return mockHttpSession;
	}}
//		
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//		SimpleDateFormat sdfTime = new SimpleDateFormat("HH/MM/SS");//時間
//		
		//タイムスタンプの変換
//		String str = "2022/12/10";
///		Date date = sdf.parse(str);
//		String strTime = "14/00/00";
//		Date dateTime = sdf.parse(strTime);
//		 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//		Timestamp timestamp3 = new Timestamp(dateTime.getTime());
//		Timestamp timestamp2 = new Timestamp(dateTime.getTime());
//
	
	   
	

	
		
	
	
//		private static MockHttpSession createMockHttpSession1(Map<String, Object> sessions) {
//			MockHttpSession mockHttpSession = new MockHttpSession();
//		for (Map.Entry<String, Object> session : sessions.entrySet()) {
//				mockHttpSession.setAttribute(session.getKey(), session.getValue());
//			return mockHttpSession;
//			}}}


