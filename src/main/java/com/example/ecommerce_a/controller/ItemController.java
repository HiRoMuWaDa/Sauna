package com.example.ecommerce_a.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Option;
import com.example.ecommerce_a.form.SearchItemByNameForm;
import com.example.ecommerce_a.service.ItemService;
import com.example.ecommerce_a.service.OptionService;

@Controller
@RequestMapping("/shop")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private OptionService optionService;

	@RequestMapping("")
	public String showList(SearchItemByNameForm form, Model model) {
		List<Item> itemList = null;
		
		if (form.getSort() == 0) {	// アイテム一覧画面を初期表示した時
			itemList = itemService.showList();
			
		} else {	// アイテム一覧画面で検索した時
			Map<Integer, String> sortMap = new LinkedHashMap<>();
			sortMap.put(1, "price_s");
			sortMap.put(2, "price_m");
			sortMap.put(3, "price_l");
			sortMap.put(4, "price_s DESC");
			sortMap.put(5, "price_m DESC");
			sortMap.put(6, "price_l DESC");
			String sort =sortMap.get(form.getSort());	// ソートのIDを文字列に変換
			itemList = itemService.showListSort(sort);
		}
		
		model.addAttribute("itemList", itemList);
		return "item_list";
	}

	@ModelAttribute
	public SearchItemByNameForm setUpSearchItemByNameForm() {
		return new SearchItemByNameForm("", 0);	// 検索キーワード空文字、sサイズ安い順をデフォルト設定にする
	}

	@RequestMapping("/search-item-by-name")
	public String searchItemByName(SearchItemByNameForm form, Model model) {
		System.out.println("検索ワード：" + form.getSearchingName());
		
		List<Item> itemList = null;

		Map<Integer, String> sortMap = new LinkedHashMap<>();
		sortMap.put(0, "price_m DESC");
		sortMap.put(1, "price_s");
		sortMap.put(2, "price_m");
		sortMap.put(3, "price_l");
		sortMap.put(4, "price_s DESC");
		sortMap.put(5, "price_m DESC");
		sortMap.put(6, "price_l DESC");
		String sort =sortMap.get(form.getSort());	// ソートのIDを文字列に変換
		itemList = itemService.searchByNameAndSort(form.getSearchingName(), sort);
		
//		List<Item> itemList = itemService.searchByNameAndSort(form.getSearchingName());
		if (itemList.size() == 0) {
			itemList = itemService.showList();
			model.addAttribute("searchMessage", "該当する商品がありません");
		}

		model.addAttribute("itemList", itemList);
		return "item_list";
	}

	@RequestMapping("/showDetail")
	public String showDetail(Integer id, Model model) {
		Item item = itemService.showDetail(id);
		model.addAttribute("item", item);

		List<Option> optionList = optionService.findAll();
		model.addAttribute("optionList", optionList);

		return "item_detail";
	}
	
}
