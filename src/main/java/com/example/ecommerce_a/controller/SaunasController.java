package com.example.ecommerce_a.controller;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Review;
import com.example.ecommerce_a.domain.Sauna;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.ReviewForm;
import com.example.ecommerce_a.form.SearchBySaunaForm;
import com.example.ecommerce_a.service.ReviewService;
import com.example.ecommerce_a.service.SaunasService;
import com.example.ecommerce_a.service.UserService;

@Controller
@RequestMapping("/research")
public class SaunasController {
	@Autowired
	private SaunasService saunasService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserService userService;
	
	// 検索用フォーム
	@ModelAttribute
	public SearchBySaunaForm setUpForm() {
		return new SearchBySaunaForm();
	}
	
	// レビューフォームの初期化
	@ModelAttribute
	public ReviewForm setUpReviewForm() {
		return new ReviewForm();
	}

	///////////////////////////////////////////
	//				一覧表示					 //
	///////////////////////////////////////////
	/**
	 * サウナ施設の一覧を表示をするメソッド
	 * エリアを選択できるようにするために、セレクトボックスにいれる値を設定する
	 * @param model
	 * @return サウナ施設一覧表示画面
	 */
	@RequestMapping("")
	public String showList(Model model) {
		List<Sauna> saunaList = saunasService.showList();
		model.addAttribute("saunaList", saunaList);
		
		Map<Integer, String> areaMap = new LinkedHashMap<>();
		areaMap.put(1, "足立区");
		areaMap.put(2, "荒川区");
		areaMap.put(3, "板橋区");
		areaMap.put(4, "江戸川区");
		areaMap.put(5, "大田区");
		areaMap.put(6, "葛飾区");
		areaMap.put(7, "北区");
		areaMap.put(8, "江東区");
		areaMap.put(9, "品川区");
		areaMap.put(10, "渋谷区");
		areaMap.put(11, "新宿区");
		areaMap.put(12, "杉並区");
		areaMap.put(13, "墨田区");
		areaMap.put(14, "世田谷区");
		areaMap.put(15, "台東区");
		areaMap.put(16, "千代田区");
		areaMap.put(17, "中央区");
		areaMap.put(18, "豊島区");
		areaMap.put(19, "中野区");
		areaMap.put(20, "練馬区");
		areaMap.put(21, "文京区");
		areaMap.put(22, "港区");
		areaMap.put(23, "目黒区");
		
		model.addAttribute("areaMap", areaMap);
		
		return "sauna_list";
	}
	
	///////////////////////////////////////////
	//				詳細表示					 //
	///////////////////////////////////////////
	
	/**
	 * サウナ施設の詳細情報とレビューを表示する
	 * この画面に同時にレビューを表示する
	 * @param id
	 * @param model
	 * @return サウナ施設の詳細情報画面
	 */
	@RequestMapping("/sauna-showDetail")
	public String showDetail(Integer id, Model model) {
		
		
		Sauna sauna = saunasService.showDetail(id);
		
		// saunaListに値は入っていることは確認済
		System.out.println("1111234567890123456789023456789");
		System.out.println(sauna);
		
		if(sauna.getReviewList().size() == 0) {
			model.addAttribute("reviewZeroMessage", "まだ口コミがありません。");
		}
		
		// URLにセットするためのidを格納
		// idをセットし、該当の施設の詳細情報が表示されるように設定している
		model.addAttribute("sauna", sauna);
		
		return "sauna_detail";
	}
	
	///////////////////////////////////////////
	//				レビュー機能				　//
	///////////////////////////////////////////
	/**
	 * コメントを投稿	 
	 * @param form
	 *            フォーム
	 * @param model
	 *            モデル
	 * @return 掲示板画面
	 */
	@RequestMapping("/post-review")
	public String postcomment(ReviewForm form, Model model) {
		
		Review review = new Review();
		System.out.println(form);
		
		
		BeanUtils.copyProperties(form, review);
		System.out.println(review);
		
		reviewService.save(review);
		model.addAttribute("review", review);
		
		System.out.println("1234567890-");
		System.out.println(form.getName());
		System.out.println(form.getReview());
		System.out.println(form.getSaunasId());
		
		// ここからポイントシステム
		// 1.現状のユーザー情報をセッションからもらってくる
		// 2.サービスを使って、DBのポイントを増やす
		// 3.セッションにポイントが更新されたユーザー情報を再度格納する。
		User nowUser = (User) session.getAttribute("user");
		Integer point = 5;
		User pointAddUser = userService.updatePoint(nowUser, point);
		session.setAttribute("user", pointAddUser);

		return "redirect:/research/sauna-showDetail?id=" + form.getSaunasId();
	}
	
	/**
	 * レビューを削除
	 * 
	 * @param form
	 *            レビューフォーム
	 * @return サウナ施設詳細画面
	 */
	@RequestMapping("/delete-review")
	public String deleteReview(int id, int saunasId) {
		System.out.println("削除したいレビューID:" + id);
		saunasService.deleteReview(id);
		return "redirect:/research/sauna-showDetail?id=" + saunasId;
	}
	
	
	///////////////////////////////////////////
	//				検索機能					 //
	///////////////////////////////////////////
	
	/**
	 * 施設名で検索をするためのメソッド
	 * 曖昧検索可能
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping("/search-sauna-by-name")
	public String searchItemByName(SearchBySaunaForm form, Model model) {
		System.out.println("検索ワード：" + form.getSearchingName());
		List<Sauna> saunaList = saunasService.searchByName(form.getSearchingName());
		Map<Integer, String> areaMap = new LinkedHashMap<>();
		areaMap.put(1, "足立区");
		areaMap.put(2, "荒川区");
		areaMap.put(3, "板橋区");
		areaMap.put(4, "江戸川区");
		areaMap.put(5, "大田区");
		areaMap.put(6, "葛飾区");
		areaMap.put(7, "北区");
		areaMap.put(8, "江東区");
		areaMap.put(9, "品川区");
		areaMap.put(10, "渋谷区");
		areaMap.put(11, "新宿区");
		areaMap.put(12, "杉並区");
		areaMap.put(13, "墨田区");
		areaMap.put(14, "世田谷区");
		areaMap.put(15, "台東区");
		areaMap.put(16, "千代田区");
		areaMap.put(17, "中央区");
		areaMap.put(18, "豊島区");
		areaMap.put(19, "中野区");
		areaMap.put(20, "練馬区");
		areaMap.put(21, "文京区");
		areaMap.put(22, "港区");
		areaMap.put(23, "目黒区");
		
		model.addAttribute("areaMap", areaMap);
		
		// 空文字の場合
		if (form.getSearchingName().isEmpty()) {
			saunaList = saunasService.showList();
			model.addAttribute("saunaList", saunaList);
			return "sauna_list";
		} 
		
		if (saunaList.size() == 0) {
			saunaList = saunasService.showList();
			model.addAttribute("saunaList", saunaList);
			model.addAttribute("searchMessage", "該当する施設がありません");
			return "sauna_list";
		} 
		
		saunaList = saunasService.searchByName(form.getSearchingName());

		model.addAttribute("saunaList", saunaList);
		return "sauna_list";
	}
	
	/**
	 * 選択したエリアで検索するメソッド
	 * 複数選択可能
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping("/search-sauna-by-area")
	public String searchByArea(SearchBySaunaForm form, Model model) {
		System.out.println("検索ワード：" +  form.getSearchingArea());
		Map<Integer, String> areaMap = new LinkedHashMap<>();
		areaMap.put(1, "足立区");
		areaMap.put(2, "荒川区");
		areaMap.put(3, "板橋区");
		areaMap.put(4, "江戸川区");
		areaMap.put(5, "大田区");
		areaMap.put(6, "葛飾区");
		areaMap.put(7, "北区");
		areaMap.put(8, "江東区");
		areaMap.put(9, "品川区");
		areaMap.put(10, "渋谷区");
		areaMap.put(11, "新宿区");
		areaMap.put(12, "杉並区");
		areaMap.put(13, "墨田区");
		areaMap.put(14, "世田谷区");
		areaMap.put(15, "台東区");
		areaMap.put(16, "千代田区");
		areaMap.put(17, "中央区");
		areaMap.put(18, "豊島区");
		areaMap.put(19, "中野区");
		areaMap.put(20, "練馬区");
		areaMap.put(21, "文京区");
		areaMap.put(22, "港区");
		areaMap.put(23, "目黒区");
		
		model.addAttribute("areaMap", areaMap);
		
		
		// 対応する文字列に変換したリスト
		List<String> areaList = new ArrayList<>();
	
		// チェックボックスを選択せずに検索した場合
		if (form.getSearchingArea() == null) {
			List<Sauna> saunaList = saunasService.showList();
			model.addAttribute("saunaList", saunaList);
//			model.addAttribute("noArea", "該当する施設がありません");
			return "sauna_list";
		} 
		
		for (int areaCode : form.getSearchingArea()) {
			switch (areaCode) {
			case 1:
				areaList.add("足立区");
				break;
			case 2:
				areaList.add("荒川区");
				break;
			case 3:
				areaList.add("板橋区");
				break;
			case 4:
				areaList.add("江戸川区");
				break;
			case 5:
				areaList.add("大田区");
				break;
			case 6:
				areaList.add("葛飾区");
				break;
			case 7:
				areaList.add("北区");
				break;
			case 8:
				areaList.add("江東区");
				break;
			case 9:
				areaList.add("品川区");
				break;
			case 10:
				areaList.add("渋谷区");
				break;
			case 11:
				areaList.add("新宿区");
				break;
			case 12:
				areaList.add("杉並区");
				break;
			case 13:
				areaList.add("墨田区");
				break;
			case 14:
				areaList.add("世田谷区");
				break;
			case 15:
				areaList.add("台東区");
				break;
			case 16:
				areaList.add("千代田区");
				break;
			case 17:
				areaList.add("中央区");
				break;
			case 18:
				areaList.add("豊島区");
				break;
			case 19:
				areaList.add("中野区");
				break;
			case 20:
				areaList.add("練馬区");
				break;
			case 21:
				areaList.add("文京区");
				break;
			case 22:
				areaList.add("港区");
				break;
			case 23:
				areaList.add("目黒区");
				break;
			}
			
		}
		
		

		// 選択したエリアに施設が登録されていない時
		if (form.getSearchingArea().equals(areaList)) {
			List<Sauna> saunaList = saunasService.showList();
			model.addAttribute("saunaList", saunaList);
			return "sauna_list";
		} 
		
		
		List<Sauna> saunaList = saunasService.searchByArea(areaList);
		model.addAttribute("saunaList", saunaList);
		
		if (saunaList.size() == 0) {
			System.out.println("動いてる？");
			saunaList = saunasService.showList();
			model.addAttribute("saunaList", saunaList);
			model.addAttribute("noArea", "該当する施設がありません");
			return "sauna_list";
		} 
		
		return "sauna_list";
	}
	
	
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/research";
	}
	
	@RequestMapping("/login")
	public String login() {
		session.setAttribute("beforeLogin", "sauna");
		return "redirect:/shop/login";
	}
}
