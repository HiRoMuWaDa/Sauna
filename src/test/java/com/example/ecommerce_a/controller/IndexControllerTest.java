package com.example.ecommerce_a.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest                                                                   
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class // このテストクラスでDIを使えるように指定
})
class IndexControllerTest {

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

	@Test
	void testIndex() throws Exception {
		//①コントローラ呼び出し
		mockMvc.perform(get("/")).andExpect(view().name("index")).andReturn();
	}
}
