package com.example.ecommerce_a.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce_a.repository.AutoCompleteRepository;

/** 商品検索フォームのオートコンプリート用コントローラ. */
@RestController
@RequestMapping("/shop")
public class AutoCompleteApiController {

	@Autowired
	private AutoCompleteRepository autoCompleteRepository;

	/** 全商品名をオートコンプリートに渡す. */
	@GetMapping("/search-item-by-name")
	public List<String> autoCompleteList() {
		List<String> allNames = autoCompleteRepository.getAllNames();
		return allNames;
	}
}