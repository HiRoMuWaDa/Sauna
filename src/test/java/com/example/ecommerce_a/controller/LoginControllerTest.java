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

import com.example.ecommerce_a.form.LoginForm;
import com.example.ecommerce_a.util.CsvDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

//おまじない
@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
        TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})
//おまじない終了
// src/test/javaの下にcom.example.ecommerce_a.util CsvDataSetLoader.javaのデータを追加する。

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
//		記述必要
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	
	
	}
//　Controllerが正しく起動しているかを確認
	
//	テスト用ユーザー登録を＠DBUnitで行う
//	その後、全てのメソッドが実行されるようにテストを実施

	@AfterEach
	void tearDown() throws Exception {
	}
	
	LoginController log = new LoginController();
	
	@Test
	@DisplayName("ログイン画面表示（正常）")
	void  test ()throws Exception{
		 mockMvc.perform(get("/shop/login"))
         .andExpect(view().name("login"));
	}
	
	/*
	 * DBUnitにデータを渡す→xml,txt,csv,xlsxなどがある。 それをどこに配置したら良いのか
	 * →src/test/resoursesの下に配置する。
	 * テキストデータをどうやってDBに渡したら良いのかが分からない。
	 * →＠DatabaseSetup（"アクセスURL"）で値を渡す。MvcResult mvcResult = mockMvc.perform(get("/userPage")
	 * DBに渡した上でどうやってログインコントローラーを確認したら良いのかが分からない。
	 * →何が返ってくるのかによって変わる。
	 * loginメソッドを呼び出して引数（LoginForm,Model）に何を入れたらいいのか　この考え方が間違ってる気がする
	 * →Controllerのメソッドは直接呼び出せない。メソッドのURLでHTMLにアクセスしてテストする
	 * txt、アノテーションを用いたDBユニットの記事が少なすぎる。csvで書き直した方が良いのか？
	 * →今回はcsvで記述する
	 * テキストデータで渡すのは事前にサイトで会員登録を行っているユーザーのメールアドレス、パスワードの認識で合っているのか。
	 * →DBUnitはテスト用としてテーブルにデータを追加するもの。（わざわざSQLで登録するのは手間だから）
	 * そのデータを用いてテストを行う。
	 */
	
	@Test
	@DisplayName("ログイン失敗")
	//@DatabaseSetup("/userPass")//csvはダブルクリックで開くとNumbersファイルになってしまうため右クリック→開くで編集する。
	void test2() throws Exception{
			MvcResult mvcResult = mockMvc.perform(get("/shop/login-result")
					.param("email", "aaa")
					.param("password", "111"))
                    .andExpect(view().name("login"))
                    .andReturn();
			
//			MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
			ModelAndView mav = mvcResult.getModelAndView();
			String errorMessage = (String) mav.getModel().get("errorMessage");
		    assertEquals("メールアドレスまたはパスワードが不正です。", errorMessage);
	}
	
	@Test
	@DisplayName("ログイン成功、カートの中に何もない状態")
	@DatabaseSetup("/userPass")
	void test3() throws Exception{
			MvcResult mvcResult = mockMvc.perform(get("/shop/login-result")
					.param("email", "sample@gmail.com")
//					パスワード登録はハッシュ化で！（csvのDB）下ではハッシュ化コマンドが実行されるためそのまま入力
					.param("password", "abababab"))
					.andExpect(view().name("redirect:/shop"))
                    .andReturn();
			
			MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
			String name = (String) session.getAttribute("name");
		    assertEquals("テストパスさんこんにちは！", name);
	
		    
//		    セッションのカート情報　Order＝Cart　OrderItemList　OrderをNew
		    
	}
	@Test
	@DisplayName("ログイン成功→カートの中に入れている状態")
	@DatabaseSetup("/cartItem")
	void test4() throws Exception{
			MvcResult mvcResult = mockMvc.perform(get("/shop/login-result")
					.param("email", "sample@gmail.com")
					.param("password", "abababab"))
                    .andReturn();
			
			MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
			String name = (String) session.getAttribute("name");
		    assertEquals("テストパスさんこんにちは！", name);
			
	
	}
	
	@Test
	@DisplayName("ログアウト画面の表示")
	void  testppp()throws Exception{
			 mockMvc.perform(get("/shop/logout"))
	         .andExpect(view().name("/shop"));
	}

	

}
