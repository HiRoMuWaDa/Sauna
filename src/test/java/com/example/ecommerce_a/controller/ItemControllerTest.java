package com.example.ecommerce_a.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.util.CsvDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) 
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
	TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})

class ItemControllerTest {

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
	@DisplayName("商品一覧画面表示")
	void showList() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/shop")).andExpect(view().name("item_list")).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		Item item = itemList.get(0);
		assertEquals(11, itemList.size());
		assertEquals("パーカー黄",item.getName(),"error");

	}
	
	@Test
	@DisplayName("商品検索(パーカー)")
	void searchItemByName() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/shop/search-item-by-name")
				.param("searchingName", "パーカー")
				.param("sort", "1"))
				.andExpect(view().name("item_list"))
				.andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		Item item = itemList.get(0);
		assertEquals("パーカー黒", item.getName(), "error");

	}

	@Test
	@DisplayName("商品詳細")
	void itemDetail() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/shop/showDetail").param("id", "11"))
				.andExpect(view().name("item_detail")).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		Item itemDetail = (Item) mav.getModel().get("item");
		assertEquals("パーカー黄", itemDetail.getName(), "error");

	}

}