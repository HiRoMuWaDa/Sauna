package com.example.ecommerce_a.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
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


	
	@ExpectedDatabase(value = "/user_pointEx", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@DatabaseSetup("/user_point")
	@Test
	@DisplayName("注文確認画面(ItemList)")
	void test3() throws Exception {

		MockHttpSession session = SessionUtil.createShoppingCartIdAdItemSession();// ログイン処理
		MvcResult mvcResult = mockMvc.perform(get("/shop/orderConfirm")// 元のURL
				.session(session)// セッションに入る

		).andExpect(view().name("order_confirm_pointUsable"))// 遷移先のHTML
				.andReturn();
	
		// orderItemListの確認
		HttpSession afterSession = mvcResult.getRequest().getSession();//←sessionの呼び出し
		List<OrderItem> orderItemList = (List<OrderItem>) session.getAttribute("orderItemlist");
		//controllerのsession.setAttribute("orderItemList", orderItemList);の中身をとる
		
			
		OrderItem orderItem1 = orderItemList.get(0);
		assertEquals(1, orderItem1.getId(), "注文確認画面商品１件目エラー");
//		assertEquals(3, orderItemList.size(), "注文確認画面商品件数エラー");
		//orderItemの取り出し方（for文？）
		//shoppingCartがnull
		
	}
     //ポイント関連
        
		@DisplayName("注文確認画面(ポイントリスト)")
		void test4() throws Exception {

			MockHttpSession session = SessionUtil.createShoppingCartIdAdItemSession();// ログイン処理
			MvcResult mvcResult = mockMvc.perform(get("/shop/orderConfirm")
					.session(session)
					).andExpect(view().name("order_confirm_pointUsable"))// 遷移先のHTML
					.andReturn();// 元のURL

		
		// usablePointListの確認（リクエストｽｺｰﾌﾟからの取り出し）
		ModelAndView mav = mvcResult.getModelAndView();//modelの使用
		List<Integer> usablePointlist = (List<Integer>) mav.getModel().get("usablePointList");
		
		for(int i=0;i<usablePointlist.size();i++) {
			usablePointlist.add(i);
		}
//		assertEquals(100,usablePointlist, "注文確認画面商品件数エラー");
	
	}
		//useblePointListから一つ取り出すのか
		//どうやってuseblePointの期待値を作るのか
		
		
	

//		@DisplayName("注文確認画面(配達時間が3時間以内)")
//		void test5() throws Exception {
//			MockHttpSession session = SessionUtil.createShoppingCartIdAｄItemSession();// ログイン処理
//			MvcResult mvcResult = mockMvc.perform(get("/shop/orderConfirm")
//					.session(session)
//					).andExpect(view().name("order_confirm_pointUsable"))// 遷移先のHTML
//					.andReturn();
//			ModelAndView mav = mvcResult.getModelAndView();//modelの使用
//			List<Integer> usablePointlist = (List<Integer>) mav.getModel().get("deliveryTimeError");
//			
//				// Modelの使用？
//		}        
		
			@Test
			@DatabaseSetup("/order_confirm")
			@DisplayName("オーダー日時(成功)")
			void testOrderCompletion01() throws Exception{
				MockHttpSession session = SessionUtil.createShoppingCartIdAdItemSession();
				
				
				
				mockMvc.perform(get("/shop/order-confirmed")
						.session(session)
						.param("deliveryTime","2022-12-10 10:00:36")
						.param("destinationName","テストユーザ")
						.param("destinationEmail","coffeeshop.test@gmail.com")
						.param("destinationZipcode","111-1111")
						.param("destinationAddress","テスト住所")
						.param("destinationTel","テスト電話番号")
					   .param("paymentMethod","1")
				        )
						.andExpect(view().name("redirect:/shop/order-finished"));
			}
			
			//setでCSVを流した後にコードを流す ショッピングカートの情報を流す
			@DisplayName("注文履歴確認画面(件数あり)")

			// @ExpectedDatabase()→処理が終わった時のテーブルの状態をCSVに（期待値をCSVに記載））DatabaseAssertionMode→書かれているCSVのみ判定

//				    @ExpectedDatabase(value = "/shop/show_order_history", assertionMode = DatabaseAssertionMode.NON_STRICT)
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
					assertEquals(1, orderItem.getItemId(), "idが一致していない");}
		//エラー内容→charの文字数を超える
		//DB,VSコードの文字化け確認
				
				}
				@DisplayName("注文履歴確認画面(件数あり)")

				// @ExpectedDatabase()→処理が終わった時のテーブルの状態をCSVに（期待値をCSVに記載））DatabaseAssertionMode→書かれているCSVのみ判定

//					    @ExpectedDatabase(value = "/shop/show_order_history", assertionMode = DatabaseAssertionMode.NON_STRICT)
				@Test
				
				    @DatabaseSetup("/test_order")

				void OrderHisttoryTest_A() throws Exception {
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
						OrderItem orderItemNull = orderItemList.get(0);
						
						assertEquals(1, orderItem.getItemId(), "idが一致していない");
						
			//エラー内容→charの文字数を超える
			//DB,VSコードの文字化け確認
					
					}
				
				

				//orderItemにはorderList<OrderItem> orderItemListがあるため
				//orderHistoryから取り出して全要素をorder型→orderitemに格納

			}
			
};
		
		
	
	
	
	
		
			
			

		
	

	
		
	
        
       
		

	
	


