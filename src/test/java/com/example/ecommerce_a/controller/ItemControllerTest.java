package com.example.ecommerce_a.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
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
		assertEquals("パーカー黄", item.getName(), "error");

	}

	@Test
	@DisplayName("商品並び替え(Sサイズ価格安い)")
	void switchItemS() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/shop").param("sort", "1")).andExpect(view().name("item_list"))
				.andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		Item item = itemList.get(0);
		assertEquals("靴下白", item.getName(), "error");

	}

	@Test
	@DisplayName("商品並び替え(Mサイズ価格安い)")
	void switchItemM() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/shop").param("sort", "2")).andExpect(view().name("item_list"))
				.andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		Item item = itemList.get(0);
		assertEquals("靴下白", item.getName(), "error");

	}

	@Test
	@DisplayName("商品並び替え(Lサイズ価格安い)")
	void switchItemL() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/shop").param("sort", "3")).andExpect(view().name("item_list"))
				.andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		Item item = itemList.get(0);
		assertEquals("靴下白", item.getName(), "error");

	}

	@Test
	@DisplayName("商品並び替え(Sサイズ価格高い)")
	void switchItemSDesc() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/shop").param("sort", "4")).andExpect(view().name("item_list"))
				.andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		Item item = itemList.get(0);
		assertEquals("ジャケット青", item.getName(), "error");

	}

	@Test
	@DisplayName("商品並び替え(Mサイズ価格高い)")
	void switchItemMDesc() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/shop").param("sort", "5")).andExpect(view().name("item_list"))
				.andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		Item item = itemList.get(0);
		assertEquals("パーカー黄", item.getName(), "error");

	}

	@Test
	@DisplayName("商品並び替え(Lサイズ価格高い)")
	void switchItemLDesc() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/shop").param("sort", "6")).andExpect(view().name("item_list"))
				.andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		Item item = itemList.get(0);
		assertEquals("サウナタオル白", item.getName(), "error");

	}

	// sortの0と5が一緒なので0は省略
	@Test
	@DisplayName("商品検索(白、Sサイズ価格安い)")
	void searchItemByNameS() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(post("/shop/search-item-by-name").param("searchingName", "白").param("sort", "1"))
				.andExpect(view().name("item_list")).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		Item item = itemList.get(0);
		assertEquals("靴下白", item.getName(), "error");

	}

	@Test
	@DisplayName("商品検索(白、Mサイズ価格安い)")
	void searchItemByNameM() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(post("/shop/search-item-by-name").param("searchingName", "白").param("sort", "2"))
				.andExpect(view().name("item_list")).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		Item item = itemList.get(0);
		assertEquals("靴下白", item.getName(), "error");

	}

	@Test
	@DisplayName("商品検索(白、Lサイズ価格安い)")
	void searchItemByNameL() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(post("/shop/search-item-by-name").param("searchingName", "白").param("sort", "3"))
				.andExpect(view().name("item_list")).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		Item item = itemList.get(0);
		assertEquals("靴下白", item.getName(), "error");

	}

	@Test
	@DisplayName("商品検索(白、Sサイズ価格高い)")
	void searchItemByNameSDesc() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(post("/shop/search-item-by-name").param("searchingName", "白").param("sort", "4"))
				.andExpect(view().name("item_list")).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		Item item = itemList.get(0);
		assertEquals("パーカー白", item.getName(), "error");

	}

	@Test
	@DisplayName("商品検索(白、Mサイズ価格高い)")
	void searchItemByNameMDesc() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(post("/shop/search-item-by-name").param("searchingName", "白").param("sort", "5"))
				.andExpect(view().name("item_list")).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		Item item = itemList.get(0);
		assertEquals("パーカー白", item.getName(), "error");

	}

	@Test
	@DisplayName("商品検索(白、Lサイズ価格高い)")
	void searchItemByNameLDesc() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(post("/shop/search-item-by-name").param("searchingName", "白").param("sort", "6"))
				.andExpect(view().name("item_list")).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		Item item = itemList.get(0);
		assertEquals("サウナタオル白", item.getName(), "error");

	}
	
	@Test
	@DisplayName("商品検索(aaaa、該当なし)")
	void searchItemByNameNull() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/shop/search-item-by-name").param("searchingName", "aaaa"))
				.andExpect(view().name("item_list")).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		String Message = (String) mav.getModel().get("searchMessage");
		assertEquals("該当する商品がありません", Message, "error");

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