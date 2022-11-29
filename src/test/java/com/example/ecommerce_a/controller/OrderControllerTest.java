package com.example.ecommerce_a.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.form.OrderConfirmForm;
import com.example.ecommerce_a.util.CsvDataSetLoader;
import com.example.ecommerce_a.util.SessionUtil;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@SpringBootTest
//@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})

class OrderControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@DisplayName("注文履歴確認画面")

	// @ExpectedDatabase()→処理が終わった時のテーブルの状態をCSVに（期待値をCSVに記載））DatabaseAssertionMode→書かれているCSVのみ判定

//		    @ExpectedDatabase(value = "/shop/show_order_history", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@Test
	
	    @DatabaseSetup("/test_order")

	void insert_1() throws Exception {
		MockHttpSession session = SessionUtil.createUserIdAndUserSession();
		MvcResult mvcResult = mockMvc.perform(get("/shop/show_order_history")// 元のURL
				.session(session)// セッションに入る
		).andExpect(view().name("order_history"))// 遷移先のHTML
				.andReturn();
		ModelAndView mav = mvcResult.getModelAndView();

		@SuppressWarnings(value = "unchecked")
		List<Order> orderHistory = (List<Order>) mav.getModel().get("orderHistory");
		for (Order order : orderHistory) {
			List<OrderItem> orderItemList = order.getOrderItemList();

			OrderItem orderItem = orderItemList.get(0);
			assertEquals(1, orderItem.getItemId(), "idが一致していない");

		}
		
		

//		    	  System.out.println(item);
//		    	  assertEquals("orderHistory",orderHistory);

	}
	
	@Test
	@DisplayName("注文完了画面")
	void test2() throws Exception {
		MockHttpSession session = SessionUtil.createUserIdAndUserSession();
		MvcResult mvcResult = mockMvc.perform(get("/shop/order-finished")// 元のURL
				.session(session)// セッションに入る
		).andExpect(view().name("order_finished"))// 遷移先のHTML
				.andReturn();
		ModelAndView mav = mvcResult.getModelAndView();

//		@SuppressWarnings(value = "unchecked")
//		List<Order> orderHistory = (List<Order>) mav.getModel().get("orderHistory");

	}

	@ExpectedDatabase(value = "/user_point", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@Test
	@DisplayName("注文確認画面")
	void test3() throws Exception {

		MockHttpSession session = SessionUtil.createShoppingCartIdAｄItemSession();// ログイン処理
		MvcResult mvcResult = mockMvc.perform(get("/shop/orderConfirm")// 元のURL
				.session(session)// セッションに入る

		).andExpect(view().name("order_confirm_pointUsable"))// 遷移先のHTML
				.andReturn();
		
		//ポイント使用
		 OrderConfirmForm pointform=new OrderConfirmForm();
		 

//		MockHttpSession mockSession = (MockHttpSession) mvcResult.getRequest().getSession();
//		List<Integer> usablePointList = (List<Integer>) mav.getModel().get("usablePointList");
//		for (Integer pointList :usablePointList ) {
//			List<Integer> orderItemList = order.getOrderItemList();
//			
//
//			OrderItem orderItem = orderItemList.get(0);
//			assertEquals(1, orderItem.getItemId(), "idが一致していない");
//
//		}
//		
//	
		
//		@SuppressWarnings(value = "unchecked")
//		List<Integer> usablePointList = (List<Integer>) mav.getModel().get("usablePointList");
		

//		System.out.println("order = " + order);
		

	};
	
	

};
