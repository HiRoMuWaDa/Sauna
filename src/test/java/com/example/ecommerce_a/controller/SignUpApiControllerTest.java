package com.example.ecommerce_a.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.ecommerce_a.util.CsvDataSetLoader;
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
		System.setOut(new PrintStream(outContent));
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.setOut(originalOut);
	}

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("動作表示（正常）")
	void  test1()throws Exception{
		mockMvc.perform(get("/shop/checkConPass"));
		assertEquals("動いてるよん",out.readLine());
		
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(out));
//		
//		 mockMvc.perform(get("/shop/checkConPass"));
//		 assertThat(out.toString(),("" + System.lineSeparator()));
	}
	}
	


