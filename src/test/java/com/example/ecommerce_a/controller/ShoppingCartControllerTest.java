package com.example.ecommerce_a.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.util.SessionUtil;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
	TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})

class ShoppingCartControllerTest {

	Order shoppingCart = new Order();

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	private MockHttpSession mockHttpSession;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		mockHttpSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());
	}

	@AfterEach
	void tearDown() throws Exception {
	}
//カバレッジは通っているけどassertionは未実装が多い
	@Test
	@DisplayName("ログインしてない状態 shoppingCartが空")
	void withoutLoginEmpty() throws Exception {
		MvcResult mvcResult=mockMvc.perform(get("/shop/cart/view"))
				.andExpect(status().isOk())
				.andExpect(view().name("cart_list"))
				.andExpect(model().attribute("nullMessage", "お客様のカートに商品はありません。"))
				.andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<OrderItem>orderList=(List<OrderItem>)mav.getModel().get("OrderItem");
		assertEquals(null,orderList,"error");
		
	}

	@Test
	@DisplayName("ログインしてない状態 shoppingCartが空ではない")
	void wihtoutLoginNotEmpty() throws Exception {
		Order dummy = new Order();
		List<OrderItem> dummyItemList = new ArrayList<>();
		dummyItemList.add(new OrderItem());
		dummy.setOrderItemList(dummyItemList);
//		MvcResult mvcResult = mockMvc.perform(get("/shop/cart/view").sessionAttr("shoppingCart", dummy))
//				.andExpect(status().isOk()).andExpect(view().name("cart_list")).andReturn();
//		ModelAndView mav = mvcResult.getModelAndView();
//		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
//		List<OrderItem> orderList = (List<OrderItem>) mav.getModel().get("OrderItem");
//		assertEquals(null, orderList, "error");

	}

	@Test
	@DisplayName("ログインしている状態 shoppingCartが空ではない")
	void LoginNotEmpty() throws Exception {
		User dummyUser = new User();
		dummyUser.setId(1);
		MvcResult mvcResult = mockMvc.perform(get("/shop/cart/view").sessionAttr("user", dummyUser))
				.andExpect(status().isOk()).andExpect(view().name("cart_list")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<OrderItem> orderList = (List<OrderItem>) mav.getModel().get("OrderItem");
	}

	@Test
	@DisplayName("ログインしている状態 shoppingCartが空")
	@DatabaseSetup("/userPass") // テスト実行前に初期データを投入
	void LoginEmpty() throws Exception {
		User dummyUser = new User();
		dummyUser.setId(1);
		MvcResult mvcResult = mockMvc.perform(get("/shop/cart/view").sessionAttr("user", dummyUser))
				.andExpect(status().isOk()).andExpect(view().name("cart_list")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<OrderItem> orderList = (List<OrderItem>) mav.getModel().get("OrderItem");
	}
	
	@Test
	@DisplayName("オーダーはあるけどカートは空")
	@DatabaseSetup("/ShoppingCartTest") // テスト実行前に初期データを投入
	void test() throws Exception {
		mockMvc.perform(get("/shop/cart/view")).andExpect(status().isOk()).andExpect(view().name("cart_list"))
				.andExpect(model().attribute("nullMessage", "お客様のカートに商品はありません。")).andReturn();

	}
	

//	@Test
//	@DisplayName("ログインしている状態 shoppingCartがなし")
//	void LoginNothing() throws Exception {
//		User dummyUser = new User();
//		dummyUser.setId(4);
//		MvcResult mvcResult = mockMvc.perform(get("/shop/cart/view").sessionAttr("user", dummyUser))
//				.andExpect(status().isOk()).andExpect(view().name("cart_list")).andReturn();
//		ModelAndView mav = mvcResult.getModelAndView();
//		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
//		List<OrderItem> orderList = (List<OrderItem>) mav.getModel().get("OrderItem");
//	}

	@Test
	@DisplayName("insert ログインありオプションなし")
	void LoginNotOptionInsert() throws Exception {
		User dummyUser = new User();
		dummyUser.setId(4);
		MvcResult mvcResult = mockMvc.perform(get("/shop/cart/insert").sessionAttr("user", dummyUser).param("size", "M")
				.param("quantity", "1").param("itemId", "10")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする

		List<OrderItem> orderList = (List<OrderItem>) mav.getModel().get("OrderItem");
		
	}

	@Test
	@DisplayName("insert ログインありオプションあり")
	void LoginOptionInsert() throws Exception {
		User dummyUser = new User();
		dummyUser.setId(4);
		mockMvc.perform(get("/shop/cart/insert").sessionAttr("user", dummyUser).param("size", "M")
				.param("quantity", "1").param("itemId", "11").param("optionIdList", "1"));
	}

	@Test
//    @DatabaseSetup("/test_Itemcomtroller/ordering")  //テスト実行前に初期データを投入
	@DisplayName("insert ログインありオプションありカートなし")
	void LoginOptionNothingInsert() throws Exception {
		User dummyUser = new User();
		dummyUser.setId(5);
		mockMvc.perform(get("/shop/cart/insert").sessionAttr("user", dummyUser).param("size", "M")
				.param("quantity", "1").param("itemId", "11").param("optionIdList", "1"));
	}

	@Test
	@DisplayName("insert ログインなしオプションなし")
	void withoutLoginNotOptionInsert() throws Exception {
		Order dummy = new Order();
		List<OrderItem> dummyItemList = new ArrayList<>();
		dummyItemList.add(new OrderItem());
		dummy.setOrderItemList(dummyItemList);
		mockMvc.perform(get("/shop/cart/insert").sessionAttr("shoppingCart", dummy).param("size", "M")
				.param("quantity", "1").param("itemId", "10"));
	}

	@Test
	@DisplayName("insert ログインなしオプションあり")
	void withoutLoginOptionInsert() throws Exception {
		Order dummy = new Order();
		List<OrderItem> dummyItemList = new ArrayList<>();
		dummyItemList.add(new OrderItem());
		dummy.setOrderItemList(dummyItemList);
		mockMvc.perform(get("/shop/cart/insert").sessionAttr("shoppingCart", dummy).param("size", "M")
				.param("quantity", "1").param("itemId", "10").param("optionIdList", "1"));
	}

	@Test
	@DisplayName("delete ログインしている")
	void deleteLogin() throws Exception {
		User dummyUser = new User();
		dummyUser.setId(4);
		mockMvc.perform(get("/shop/cart/delete").sessionAttr("user", dummyUser).param("id", "15"))
				.andExpect(status().isFound());
	}

	@Test
	@DisplayName("delete ログインしてない")
	void deleteWithoutLogin() throws Exception {
		Order dummy = new Order();
		List<OrderItem> dummyItemList = new ArrayList<>();
		dummyItemList.add(new OrderItem());
		dummy.setOrderItemList(dummyItemList);
		mockMvc.perform(get("/shop/cart/delete").sessionAttr("shoppingCart", dummy).param("index", "0"))
				.andExpect(status().isFound());
	}

}
