package com.example.ecommerce_a.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

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

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.User;
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

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@DatabaseSetup("/user_point")
	@ExpectedDatabase(value = "/user_pointEx", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@Test
	@DisplayName("注文確認画面(ItemList)")
	void test3() throws Exception {
		Order order = new Order();
		MockHttpSession session = SessionUtil.createShoppingCartIdAdItemSession();// ログイン処理
		MvcResult mvcResult = mockMvc.perform(get("/shop/orderConfirm")// 元のURL
				.session(session)// セッションに入る

		).andExpect(view().name("order_confirm_pointUsable"))// 遷移先のHTML
				.andReturn();

		// orderItemListの確認
		HttpSession afterSession = mvcResult.getRequest().getSession();// ←sessionの呼び出し
		List<OrderItem> orderItemList = (List<OrderItem>) session.getAttribute("orderItemlist");
		// controllerのsession.setAttribute("orderItemList", orderItemList);の中身をとる
		for (OrderItem orderitem : orderItemList) {

			assertEquals(3, orderitem.getId(), "注文確認画面商品１件目エラー");
		}
	}
//		assertEquals(3, orderItemList.size(), "注文確認画面商品件数エラー");

	@DatabaseSetup("/order_confirm")
	@DisplayName("注文確認画面(ポイントリスト)")
	@Test
	void PointUseble() throws Exception {

		MockHttpSession session = SessionUtil.createShoppingCartIdAdItemSession();// ログイン処理
		MvcResult mvcResult = mockMvc.perform(get("/shop/orderConfirm").session(session))
				.andExpect(view().name("order_confirm_pointUsable"))// 遷移先のHTML
				.andReturn();// 元のURL

		// usablePointListの確認（リクエストｽｺｰﾌﾟからの取り出し）
		ModelAndView mav = mvcResult.getModelAndView();// modelの使用
		List<Integer> usablePointlist = (List<Integer>) mav.getModel().get("usablePointList");

	}
	@Test
	   
		@ExpectedDatabase(value = "/use_pointEx", assertionMode = DatabaseAssertionMode.NON_STRICT)
		@DisplayName("ポイント使用")
		void PointUsed() throws Exception {
			MockHttpSession session = SessionUtil.createOrder();
		
			MvcResult mvcResult = mockMvc.perform(get("/shop/order-confirmed")// パラメータの処理
		
					.session(session).param("destinationName", "テストユーザ")
					.param("destinationEmail", "coffeeshop.test@gmail.com").param("destinationAddress", "テスト住所")
					.param("destinationTel", "000-0000-0000").param("destinationZipcode", "111-1111")
					.param("paymentMethod", "1").param("usePoint", "100").param("deliveryDate", "2022-12-10")
					.param("deliveryTime", "14:00"))
					.andExpect(view().name("redirect:/shop/order-finished"))// 遷移先のHTML
					.andReturn();
			User use= (User) session.getAttribute("use");
		}
	
	@Test
   @DatabaseSetup("/order_finished")
	@ExpectedDatabase(value = "/order_finishedEx", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@DisplayName("オーダー内容(代引き)")
	void testOrderCompletion01() throws Exception {
		MockHttpSession session = SessionUtil.createOrder();
	
		MvcResult mvcResult = mockMvc.perform(get("/shop/order-confirmed")// パラメータの処理
	
				.session(session).param("destinationName", "テストユーザ")
				.param("destinationEmail", "coffeeshop.test@gmail.com").param("destinationAddress", "テスト住所")
				.param("destinationTel", "000-0000-0000").param("destinationZipcode", "111-1111")
				.param("paymentMethod", "1").param("usePoint", "100").param("deliveryDate", "2022-12-10")
				.param("deliveryTime", "14:00"))
				.andExpect(view().name("redirect:/shop/order-finished"))// 遷移先のHTML
				.andReturn();
		User use= (User) session.getAttribute("use");
	}
	@Test
	   @DatabaseSetup("/order_finished")
		@ExpectedDatabase(value = "/order_finishedEx", assertionMode = DatabaseAssertionMode.NON_STRICT)
		@DisplayName("オーダー内容(クレジット)")
		void testOrderCompletion02() throws Exception {
			MockHttpSession session = SessionUtil.createOrder();
		
			MvcResult mvcResult = mockMvc.perform(get("/shop/order-confirmed")// パラメータの処理
		
					.session(session).param("destinationName", "テストユーザ")
					.param("destinationEmail", "coffeeshop.test@gmail.com").param("destinationAddress", "テスト住所")
					.param("destinationTel", "000-0000-0000").param("destinationZipcode", "111-1111")
					.param("paymentMethod", "2").param("usePoint", "100").param("deliveryDate", "2022-12-10")
					.param("deliveryTime", "14:00"))
					.andExpect(view().name("redirect:/shop/order-finished"))// 遷移先のHTML
					.andReturn();
			User use= (User) session.getAttribute("use");
	}
	@DisplayName("注文エラー")

	@Test

	@DatabaseSetup("/order_error")
	@ExpectedDatabase(value = "/order_finishedEx", assertionMode = DatabaseAssertionMode.NON_STRICT)

	void OrderErrorTest() throws Exception {
		MockHttpSession session = SessionUtil.createOrder();
		
		MvcResult mvcResult = mockMvc.perform(get("/shop/order-confirmed")// パラメータの処理
	
				.session(session).param("destinationName", "テストユーザ")
				.param("destinationEmail", "coffeeshop.test@gmail.com").param("destinationAddress", "テスト住所")
				.param("destinationTel", "000-0000-0000").param("destinationZipcode", "111-1111")
				.param("paymentMethod", "1").param("usePoint", "100").param("deliveryDate", "2022-12-01")
				.param("deliveryTime", "14:00"))
				.andExpect(view().name("redirect:/shop/order-finished"))// 遷移先のHTML
				.andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		mav.getModel().get("deliveryTimeError");
	}
	@DisplayName("注文履歴確認画面(件数あり)")
	@Test
	@DatabaseSetup("/test_order")

	void OrderHisttoryTest() throws Exception {
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
}
	@DisplayName("注文履歴確認画面(件数無し)")
	@Test
	void OrderHisttoryTest_A() throws Exception {
		MockHttpSession session = SessionUtil.createUserIdAndUserSession();
		MvcResult mvcResult = mockMvc.perform(get("/shop/show_order_history")// 元のURL
				.session(session)// セッションに入る
		).andExpect(view().name("order_history"))// 遷移先のHTML
				.andReturn();
	}

	@DisplayName("orderConfirm遷移")
	@Test
	void OrderConfirm() throws Exception {

		MockHttpSession session = SessionUtil.createShoppingCartIdAdItemSession();// ログイン処理
		MvcResult mvcResult = mockMvc.perform(get("/shop/order-confirm")// 元のURL
				.session(session)// セッションに入る

		).andExpect(view().name("order_confirm_pointUsable"))// 遷移先のHTML
				.andReturn();
	}

	// orderItemにはorderList<OrderItem> orderItemListがあるため
	// orderHistoryから取り出して全要素をorder型→orderitemに格納
	@DisplayName("注文確定画面")
	@Test
	void OrderFinishd() throws Exception {

		MockHttpSession session = SessionUtil.createShoppingCartIdAdItemSession();// ログイン処理
		MvcResult mvcResult = mockMvc.perform(get("/shop/order-finished")// 元のURL
				.session(session)// セッションに入る

		).andExpect(view().name("order_finished"))// 遷移先のHTML
				.andReturn();
	}

};
