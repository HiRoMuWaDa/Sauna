	package com.example.ecommerce_a.controller;
	
	import static org.junit.jupiter.api.Assertions.assertEquals;
	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
	
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
	import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
	import com.github.springtestdbunit.annotation.DatabaseSetup;
	import com.github.springtestdbunit.annotation.DbUnitConfiguration;
	
	@SpringBootTest
	@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
	@TestExecutionListeners({
	        DependencyInjectionTestExecutionListener.class, 
	        TransactionDbUnitTestExecutionListener.class
	        })
	
	class SignUpControllerTest {
		
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
	
		@Test
		@DisplayName("会員登録画面表示（正常）")
		void  test1()throws Exception{
			 mockMvc.perform(get("/shop/to-signup"))
	         .andExpect(view().name("sign-up"));
		}
		
		@Test
		@DisplayName("会員登録画面表示（エラー時）")
		void  test2()throws Exception{
			 mockMvc.perform(get("/shop/signup"))
	         .andExpect(view().name("sign-up"));
		}
		
		@Test
		@DisplayName("会員登録画面表示（確認用パスワード不一致）")
		void  test3()throws Exception{
			MvcResult mvcResult = mockMvc.perform(get("/shop/signup")
			 .param("name", "やまだはなこ")
			 .param("email","yamada@sample.com")
			 .param("password","abababab")
			 .param("zipcode", "111-1111")
			 .param("address","東京都港区")
			 .param("telephone","000-0000-0000")
			 .param("confirmationPassword","aaaaaaaa"))
	         .andExpect(view().name("sign-up"))
	         .andReturn();
			 
			 ModelAndView mav = mvcResult.getModelAndView();
			 String passMessage = (String) mav.getModel().get("passMessage");
			 assertEquals("パスワードと確認用パスワードが一致していません", passMessage);
		}
		
		@Test
		@DatabaseSetup("/userPass")
		@DisplayName("会員登録画面表示（メールアドレス重複）")
		void  test4()throws Exception{
			MvcResult mvcResult = mockMvc.perform(get("/shop/signup")
			 .param("name", "やまだはなこ")
			 .param("email","sample@gmail.com")
			 .param("password","abababab")
			 .param("zipcode", "111-1111")
			 .param("address","東京都港区")
			 .param("telephone","000-0000-0000")
			 .param("confirmationPassword","abababab"))
	         .andExpect(view().name("sign-up"))
	         .andReturn();
			
			ModelAndView mav = mvcResult.getModelAndView();
			String mailMessage = (String) mav.getModel().get("mailMessage");
		    assertEquals("このメールアドレスはすでに使用されています", mailMessage);
		}
		
		@Test
		@DatabaseSetup("/userPass")
		@DisplayName("会員登録画面表示（成功）")
		void  test5()throws Exception{
			MvcResult mvcResult = mockMvc.perform(get("/shop/signup")
			 .param("name", "やまだはなこ")
			 .param("email","yamada@sample.com")
			 .param("password","abababab")
			 .param("zipcode", "111-1111")
			 .param("address","東京都港区")
			 .param("telephone","000-0000-0000")
			 .param("confirmationPassword","abababab")
			 .param("message",""))
	         .andExpect(view().name("redirect:/shop/login"))
	         .andReturn();
			
			
			
			 
		
		}
		
	
	}
