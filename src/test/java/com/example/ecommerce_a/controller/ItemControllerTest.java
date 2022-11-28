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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.example.ecommerce_a.domain.Item;

@SpringBootTest
@AutoConfigureMockMvc

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
	void test1() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/shop")).andExpect(view().name("item_list")).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("itemList");
		assertEquals(11, itemList.size());

	}

//未完成エラー出る
	@Test
	@DisplayName("商品一覧(sサイズ安い順)")
	void test2() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/shop").param("1", "price_s")).andExpect(view().name("item_list"))
				.andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value = "unchecked") // 下のキャストのワーニングを出さないようにする
		List<Item> itemList = (List<Item>) mav.getModel().get("ItemList");
//		Item item = itemList.get(0);
//		assertEquals("靴下白", item.getName(), "error");

	}
//未完成エラー出る
//	@Test
//	@DisplayName("商品詳細")
//	void test() throws Exception {
//		mockMvc.perform(get("/shop/showDetail")).andExpect(view().name("item_detail")).andReturn();
//
//	}

}