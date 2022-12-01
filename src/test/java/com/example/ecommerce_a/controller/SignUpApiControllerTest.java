package com.example.ecommerce_a.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

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

import com.example.ecommerce_a.util.CsvDataSetLoader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, 
        TransactionDbUnitTestExecutionListener.class
        })
class SignUpApiControllerTest {
	
	private PrintStream defaultPrintStream;
	private ByteArrayOutputStream byteArrayOutputStream;
	
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
	@DisplayName("動作表示（確認用パスワード空欄）")
	void  testCheckNull()throws Exception{
		
		MvcResult mvcResult = mockMvc.perform(post("/shop/checkConPass")
		.param("password", "abababab")
		.param("confirmationPassword", ""))
		.andReturn();
		
		String result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = objectMapper.readValue(result, new TypeReference<>() {});
		assertEquals("確認用パスワードが空欄です", map.get("duplicateMessage"), "duplicateMessage");
		

	}

	
	@Test
	@DisplayName("動作表示（確認用パスワード一致）")
	void  testCheckTrue()throws Exception{
		
		MvcResult mvcResult = mockMvc.perform(post("/shop/checkConPass")
		.param("password", "abababab")
		.param("confirmationPassword", "abababab"))
		.andReturn();
		
		String result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = objectMapper.readValue(result, new TypeReference<>() {});
		
		assertEquals("確認用パスワード入力OK!",map.get("duplicateMessage"), "duplicateMessage");
	}
	
	@Test
	@DisplayName("動作表示（確認用パスワード不一致）")
	void  testCheckFalse()throws Exception{
		
		MvcResult mvcResult = mockMvc.perform(post("/shop/checkConPass")
		.param("password", "abababab")
		.param("confirmationPassword", "aaaaaaa"))
		.andReturn();
		
		String result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = objectMapper.readValue(result, new TypeReference<>() {});
		assertEquals("パスワードと確認用パスワードが一致していません", map.get("duplicateMessage"), "duplicateMessage");
	
	}
	}
	


