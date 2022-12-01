package com.example.ecommerce_a.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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

import com.example.ecommerce_a.util.CsvDataSetLoader;
import com.example.ecommerce_a.util.SessionUtil;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, 
        TransactionDbUnitTestExecutionListener.class
        })

class LoginControllerTest {
	
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
        @AfterEach
	void tearDown() throws Exception {
	}
	
	LoginController log = new LoginController();
	
	@Test
	@DisplayName("ログイン画面表示（正常）")
	void  testLogin ()throws Exception{
		 mockMvc.perform(get("/shop/login"))
         .andExpect(view().name("login"));
	}
	
	@Test
	@DisplayName("ログイン失敗")
	void testLoginMiss() throws Exception{
			MvcResult mvcResult = mockMvc.perform(get("/shop/login-result")
					.param("email", "aaa")
					.param("password", "111"))
                    .andExpect(view().name("login"))
                    .andReturn();
			ModelAndView mav = mvcResult.getModelAndView();
			String errorMessage = (String) mav.getModel().get("errorMessage");
		    assertEquals("メールアドレスまたはパスワードが不正です。", errorMessage);
	}
	
	@Test
	@DisplayName("ログイン成功、カートの中に何もない状態")
	@DatabaseSetup("/userPass")
	void testLoginCartNull() throws Exception{
			MvcResult mvcResult = mockMvc.perform(get("/shop/login-result")
					.param("email", "sample@gmail.com")
					.param("password", "abababab"))
					.andExpect(view().name("redirect:/shop"))
                    .andReturn();
			
			MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
			String name = (String) session.getAttribute("name");
		    assertEquals("テストパスさんこんにちは！", name);
	
	}
	
	@Test
	@DisplayName("ログイン成功→カートの中に入れている状態、orderConfilmにreturn")
	@DatabaseSetup("/userPass")
	void testLoginCartInToOC() throws Exception{
		
        MockHttpSession userIdSession = SessionUtil.createShoppingCartIdItemSessionToOC();
        	MvcResult mvcResult = mockMvc.perform(get("/shop/login-result")
        		.session(userIdSession)
        		.param("email", "sample@gmail.com")
        		.param("password", "abababab"))
        		.andExpect(view().name("redirect:/shop/orderConfirm"))
                .andReturn();
	}
	
	@Test
	@DisplayName("ログイン成功→カートの中に入れている状態、researchにreturn")
	@DatabaseSetup("/userPass")
	void testLoginCartInToRS() throws Exception{
		
        MockHttpSession userIdSession = SessionUtil.createShoppingCartIdItemSessionToSauna();
        MvcResult mvcResult = mockMvc.perform(get("/shop/login-result")
        		.session(userIdSession)
        		.param("email", "sample@gmail.com")
        		.param("password", "abababab"))
        		.andExpect(view().name("redirect:/research"))
                .andReturn();
	}
	
	@Test
	@DisplayName("ログイン成功→カート新規作成")
	@DatabaseSetup("/userPass")
	void testLoginCartNew() throws Exception{
        MockHttpSession userIdSession2 = SessionUtil.createShoppingCartIdItemSessionNew();
        MvcResult mvcResult = mockMvc.perform(get("/shop/login-result")
        		.session(userIdSession2)
        		.param("email", "sample@gmail.com")
        		.param("password", "abababab"))
        		.andExpect(view().name("redirect:/shop"))
                .andReturn();
        
		
	
	}
	
	@Test
	@DisplayName("ログアウト画面の表示")
	void  testLogout()throws Exception{
			 mockMvc.perform(get("/shop/logout"))
	         .andExpect(view().name("redirect:/shop"));
	}
	

	

}
