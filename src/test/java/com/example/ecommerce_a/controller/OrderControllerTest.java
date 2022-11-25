package com.example.ecommerce_a.controller;

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
import com.example.ecommerce_a.util.CsvDataSetLoader;
import com.example.ecommerce_a.util.SessionUtil;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
      DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
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
//		    
//		    @DisplayName("テスト用ログイン")
//		  
//		    @Test
//		    
//		    @DatabaseSetup("/test_login") // CVSのデータ保管場所
//		    void OrderTestLogin() throws Exception {
//		        MvcResult mvcResult = mockMvc.perform(post("/shop/show_order_history")
//		        	
//                        .param("email", "test@test.co.jp")
//                        .param("password", "test"))
//		                .andExpect(view().name("email_submit"))
//		                .andReturn();
//
//		       
//		    }
		   
		    
		    @DisplayName("注文履歴確認画面")
		  
		    //@ExpectedDatabase()→処理が終わった時のテーブルの状態をCSVに（期待値をCSVに記載））DatabaseAssertionMode→書かれているCSVのみ判定
		
		    @ExpectedDatabase(value = "/show_order_history", assertionMode = DatabaseAssertionMode.NON_STRICT)
		    @Test
	    @DatabaseSetup("/test_order")
		    void insert_1() throws Exception {
		    	MockHttpSession session = SessionUtil.createUserIdAndUserSession();
				MvcResult mvcResult = mockMvc.perform(get("/shop/show_order_history")
						.session(session)//セッションに入る
						).andExpect(view().name("order_history"))//遷移先のHTML
		    			 .andReturn();
		    	  ModelAndView mav = mvcResult.getModelAndView();
		    
		    	  @SuppressWarnings(value = "unchecked")
	    	  List<Order> orderHistory = (List<Order>) mav.getModel().get("orderHistory");
		    	  for (Order order : orderHistory) {
			    	  List<OrderItem> orderItemList =  order.getOrderItemList();
			    	  
			    	  OrderItem orderItem = orderItemList.get(0);
			    	  assertEquals(5, orderItem.getItemId(), "idが一致していない");
			    	 
			    	  
			 
		    	  }
		    	
		    	 
		    	  
//		    	  System.out.println(item);
//		    	  assertEquals("orderHistory",orderHistory);
		    	  
		    }
		              
		    };
		    




  

	
	



