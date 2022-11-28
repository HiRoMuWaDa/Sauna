package com.example.ecommerce_a.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

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

import com.example.ecommerce_a.domain.Review;
import com.example.ecommerce_a.domain.Sauna;
import com.example.ecommerce_a.form.ReviewForm;
import com.example.ecommerce_a.util.CsvDataSetLoader;
import com.example.ecommerce_a.util.SessionUtil;
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
	
	///////////////////////////////////////////
	//				一覧表示					 //
	///////////////////////////////////////////
	
	@Test
	@DisplayName("サウナ施設 一覧表示(全件表示)")
	//@DatabaseSetup(value = "/test_SaunasController/")
  	//@ExpectedDatabase(value = "/test_SaunasController", assertionMode = DatabaseAssertionMode.NON_STRICT)
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

	///////////////////////////////////////////
	//				名前検索機能				 //
	///////////////////////////////////////////
	
	@Test
	@DisplayName("サウナ施設 (検索：空文字)")
	void searchItemByName_01() throws Exception{
		//①コントローラ呼び出し
		MvcResult mvcResult = mockMvc.perform(get("/research/search-sauna-by-name")
									 .param("searchingName", ""))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
		
		//②スコープデータの呼び出し
		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value="unchecked")
		List<Sauna> saunaList = (List<Sauna>) mav.getModel().get("saunaList");
		
		//③テスト
		//先頭と後尾の温泉名

		Sauna saunaF = saunaList.get(0);
		Sauna saunaB = saunaList.get(saunaList.size()-1);
		
		assertAll(
			() -> assertEquals("ひだまりの泉　萩の湯", saunaF.getName(),"saunaF_Error"),
			() -> assertEquals("湯どんぶり栄湯", saunaB.getName(),"saunaB_Error")
		);
	}
	
	@Test
	@DisplayName("サウナ施設 (検索：あああああ[該当なし])")
	void searchItemByName_02() throws Exception{
		//①コントローラ呼び出し
		MvcResult mvcResult = mockMvc.perform(get("/research/search-sauna-by-name")
									 .param("searchingName", "あああああ"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
		
		//②スコープデータの呼び出し
		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value="unchecked")
		List<Sauna> saunaList = (List<Sauna>) mav.getModel().get("saunaList");
		String message = (String) mav.getModel().get("searchMessage");
		
		//③テスト
		//先頭と後尾の温泉名および検索メッセージのチェック
		Sauna saunaF = saunaList.get(0);
		Sauna saunaB = saunaList.get(saunaList.size()-1);
		
		assertAll(
			() -> assertEquals("ひだまりの泉　萩の湯", saunaF.getName(),"saunaF_Error"),
			() -> assertEquals("湯どんぶり栄湯", saunaB.getName(),"saunaB_Error"),
			() -> assertEquals("該当する施設がありません", message,"message_Error")
		);
	}
	
	@Test
	@DisplayName("サウナ施設 (検索：妙法湯)")
	void searchItemByName_03() throws Exception{
		//①コントローラ呼び出し
		MvcResult mvcResult = mockMvc.perform(get("/research/search-sauna-by-name")
									 .param("searchingName", "妙法湯"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
		
		//②スコープデータの呼び出し
		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value="unchecked")
		List<Sauna> saunaList = (List<Sauna>) mav.getModel().get("saunaList");
		
		//③テスト
		//該当の温泉名をチェック	
		Sauna sauna = saunaList.get(0);
		
		assertAll(
			() -> assertEquals("妙法湯", sauna.getName(),"sauna_Error")
		);
	}
	
	///////////////////////////////////////////
	//				エリア検索機能				 //
	///////////////////////////////////////////
	
	@Test
	@DisplayName("サウナ施設 (施設場所検索:null チェックボックスを選択せずに検索した場合")
	void searchItemByArea_null() throws Exception{
		//①コントローラ呼び出し
		MvcResult mvcResult = mockMvc.perform(get("/research/search-sauna-by-area")
									 )//.param("searchingArea", ""))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
		
		//②スコープデータの呼び出し
		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value="unchecked")
		List<Sauna> saunaList = (List<Sauna>) mav.getModel().get("saunaList");
		
		//③テスト
		//先頭と後尾の温泉名
		Sauna saunaF = saunaList.get(0);
		Sauna saunaB = saunaList.get(saunaList.size()-1);
		
		assertAll(
			() -> assertEquals("ひだまりの泉　萩の湯", saunaF.getName(),"saunaF_Error"),
			() -> assertEquals("湯どんぶり栄湯", saunaB.getName(),"saunaB_Error")
		);
	}
	//サウナ施設 (施設場所検索:id=1~23 id=9で該当有と23で該当なしチェック)
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=1)")
	void searchItemByArea_01() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "1"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=2)")
	void searchItemByArea_02() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "2"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=3)")
	void searchItemByArea_03() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "3"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=4)")
	void searchItemByArea_04() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "4"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=5)")
	void searchItemByArea_05() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "5"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=6)")
	void searchItemByArea_06() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "6"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=7)")
	void searchItemByArea_07() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "7"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=8)")
	void searchItemByArea_08() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "8"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}	
	@Test
	@DisplayName("サウナ施設 (施設場所検索:品川区id=9[該当有り])")
	void searchItemByArea_09() throws Exception{
		//①コントローラ呼び出し
		MvcResult mvcResult = mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "9"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
		
		//②スコープデータの呼び出し
		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value="unchecked")
		List<Sauna> saunaList = (List<Sauna>) mav.getModel().get("saunaList");
		
		//③テスト
		//該当先頭と後尾の温泉名をチェック	
		Sauna saunaF = saunaList.get(0);
		Sauna saunaB = saunaList.get(saunaList.size()-1);
		
		assertAll(
			() -> assertEquals("戸越銀座温泉", saunaF.getName(),"saunaF_Error"),
			() -> assertEquals("金春湯", saunaB.getName(),"saunaB_Error")
		);
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=10)")
	void searchItemByArea_10() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "10"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=11)")
	void searchItemByArea_11() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "11"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=12)")
	void searchItemByArea_12() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "12"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=13)")
	void searchItemByArea_13() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "13"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=14)")
	void searchItemByArea_14() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "14"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=15)")
	void searchItemByArea_15() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "15"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=16)")
	void searchItemByArea_16() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "16"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=17)")
	void searchItemByArea_17() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "17"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=18)")
	void searchItemByArea_18() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "18"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=19)")
	void searchItemByArea_19() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "19"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=20)")
	void searchItemByArea_20() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "20"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=21)")
	void searchItemByArea_21() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "21"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:id=22)")
	void searchItemByArea_22() throws Exception{
		//①コントローラ呼び出し
		mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "22"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
	}
	@Test
	@DisplayName("サウナ施設 (施設場所検索:目黒区id=23[該当無し])")
	void searchItemByArea_23() throws Exception{
		//①コントローラ呼び出し
		MvcResult mvcResult = mockMvc.perform(get("/research/search-sauna-by-area")
									 .param("searchingArea", "23"))
									 .andExpect(view().name("sauna_list"))
									 .andReturn();
		
		//②スコープデータの呼び出し
		ModelAndView mav = mvcResult.getModelAndView();
		@SuppressWarnings(value="unchecked")
		List<Sauna> saunaList = (List<Sauna>) mav.getModel().get("saunaList");
		//String message = (String) mav.getModel().get("searchMessage");
		
		//③テスト
		//該当の先頭と後尾の温泉名および検索メッセージのチェック
		Sauna saunaF = saunaList.get(0);
		Sauna saunaB = saunaList.get(saunaList.size()-1);
		
		assertAll(
			() -> assertEquals("ひだまりの泉　萩の湯", saunaF.getName(),"saunaF_Error"),
			() -> assertEquals("湯どんぶり栄湯", saunaB.getName(),"saunaB_Error")
		//() -> assertEquals("該当する施設がありません", message,"message_Error")
		);
	}
	
	///////////////////////////////////////////
	//				施設詳細表示				 //
	///////////////////////////////////////////
	
	@Test
	@DisplayName("サウナ施設詳細 (id=2)")
	void showDetail() throws Exception{
		//①コントローラ呼び出し
		MvcResult mvcResult = mockMvc.perform(get("/research/sauna-showDetail")
									 .param("id", "2"))
									 .andExpect(view().name("sauna_detail"))
									 .andReturn();
		
		//②スコープデータの呼び出し
		ModelAndView mav = mvcResult.getModelAndView();
		//@SuppressWarnings(value="unchecked")
		Sauna saunaDetail = (Sauna) mav.getModel().get("sauna");
		
		//③テスト
		//温泉名をチェック
		assertAll(
				() -> assertEquals("妙法湯", saunaDetail.getName(),"Detail_Error")
			);
	}
	
	///////////////////////////////////////////
	//				レビュー機能				　//
	///////////////////////////////////////////
	
	@Test
	@DisplayName("サウナ施設詳細 (id=2)")
	void postcomment() throws Exception{
		//①コントローラ呼び出し
	    MockHttpSession userIdSession = SessionUtil.createUserIdAndUserSession();

//	    Formでやるやり方があるはずなので後で調べる
//	    ReviewForm form = new ReviewForm();
//		    form.setName("あいうえお");
//		    form.setReview("かきくけこ");
	    
	    MvcResult mvcResult = mockMvc.perform(get("/research/post-review")
	    							 .session(userIdSession)
	    							 .param("name", "あいうえお")
	    							 .param("review", "かきくけこ")
	    							 .param("saunasId", "2"))
	    							 .andExpect(redirectedUrl("/research/sauna-showDetail?id=2"))
									 .andReturn();
		
		//②スコープデータの呼び出し
		ModelAndView mav = mvcResult.getModelAndView();
		//@SuppressWarnings(value="unchecked")
		Sauna saunaDetail = (Sauna) mav.getModel().get("sauna");
		System.out.println(saunaDetail);
		//Sauna saunaDetail = (Sauna) mav.getModel().get("sauna");
		//List<Review> reviewList = saunaDetail.getReviewList();
//		Review review = reviewList.get(0);
//		String strReview = review.getReview();
		
		//③テスト
		//温泉名をチェック
		assertAll(
				() -> assertEquals("かきくけこ", saunaDetail,"Detail_Error")
			);
	}
	
	///////////////////////////////////////////
	//			ログイン・ログアウト機能			 //
	///////////////////////////////////////////
	
//	@Test
//	@DisplayName("ログイン機能")
//    void login() throws Exception {
//		//①コントローラ呼び出し
//        MockHttpSession userIdSession = SessionUtil.createUserIdAndUserSession();
//        MvcResult mvcResult = mockMvc.perform(get("/userPage")
//                        .session(userIdSession))
//                .andExpect(view().name("redirect:/research"))
//                .andReturn();
//		
//		//②スコープデータの呼び出し
//		ModelAndView mav = mvcResult.getModelAndView();
//		@SuppressWarnings(value="unchecked")
//		
//    }
}