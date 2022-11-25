package com.example.ecommerce_a.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.example.ecommerce_a.domain.Sauna;
import com.example.ecommerce_a.util.CsvDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@SpringBootTest                                                                   
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)           
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
	TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})

class SaunasControllerTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("TestStart");
	}
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("TestFinish");	
	}
	@BeforeEach
	void setUp() throws Exception {
		System.out.println("==========Test==========");
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();   
	}
	@AfterEach
	void tearDown() throws Exception {
	System.out.println("========================");
	}

	//検索結果は全て【価格の昇順】
	@Test
	@DisplayName("サウナ施設 一覧表示(全件表示)")
	//@DatabaseSetup(value = "src/test/resources/test_SaunasController/")
	@ExpectedDatabase(value = "/test_SaunasController", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void showList()throws Exception{
		//①コントローラ呼び出し
		
		MvcResult mvcResult = mockMvc.perform(get("/research"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
				
		//②スコープのデータ取り出し
		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value="unchecked")
		List<Sauna> saunaList = (List<Sauna>) mav.getModel().get("saunaList");
		
		//③テスト
		//並び順先頭と後尾の名前をチェック	
		Sauna saunaF = saunaList.get(0);
		Sauna saunaB = saunaList.get(saunaList.size()-1);
		
		assertAll(
			() -> assertEquals("ひだまりの泉　萩の湯", saunaF.getName(),"saunaF_Error"),
			() -> assertEquals("湯どんぶり栄湯", saunaB.getName(),"saunaB_Error")
		);
	}
	
//	@Test
//	@DisplayName("サウナ施設 一覧表示(施設名検索)")
//	void searchItemByName() throws Exception{
//		//①コントローラ呼び出し
//		MvcResult mvcResult = mockMvc.perform(get("/search-sauna-by-name")
//									 .param("searchingArea", "品川区"))
//									 .andExpect(view().name("sauna_list"))
//									 .andReturn();
//		
//		//②スコープデータの呼び出し
//		ModelAndView mav = mvcResult.getModelAndView();
//		@SuppressWarnings(value="unchecked")
//		List<Sauna> saunaList = (List<Sauna>) mav.getModel().get("saunaList");
//		
//		//③テスト
//		
//		
//	}
//	
//	@Test
//	@DisplayName("サウナ施設 一覧表示(場所検索)")
//	void searchByArea() throws Exception{
//		
//	}
}
