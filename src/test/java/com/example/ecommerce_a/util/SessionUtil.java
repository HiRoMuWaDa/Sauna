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

public class SessionUtil {
	

	public static MockHttpSession createShoppingCartIdItemSession() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		
		Order order = new Order();
		List<OrderItem> orderItemList = new ArrayList<>();
//		order.setTotalPrice(10000);
		order.setOrderItemList(orderItemList);	
		//orderにorderListをセット
		
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
		//orderにorderListをセット
		
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
	

	private static MockHttpSession createMockHttpSession(Map<String, Object> sessions) {
		MockHttpSession mockHttpSession = new MockHttpSession();
		for (Map.Entry<String, Object> session : sessions.entrySet()) {
			mockHttpSession.setAttribute(session.getKey(), session.getValue());
		}
		return mockHttpSession;
	}
	}



=======
package com.example.ecommerce_a.util;

import java.util.LinkedHashMap;
import java.util.Map;


import com.example.ecommerce_a.domain.User;
import org.springframework.mock.web.MockHttpSession;


public class SessionUtil {

	public static MockHttpSession createUserIdAndUserSession() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		User user = new User();
		user.setId(1);
		user.setName("テストユーザ");
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
  
}
