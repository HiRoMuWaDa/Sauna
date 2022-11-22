package com.example.ecommerce_a.service;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.repository.ItemRepository;
@Service
@Transactional
public class ItemService {
	@Autowired
	private ItemRepository itemRepository;
	public List<Item> showList() {
		List<Item> itemList = itemRepository.findAll();
		return itemList;
	}
	
	public List<Item> showListSort(String sort) {
		
		boolean isDesc = false;	// ASC検索で初期化しておく
		
		String[] sortArray = sort.split(" ");//分けてるからsortArray[0]はprice_sなどsortArray[1]だとDESC
		
		if (Arrays.asList(sortArray).contains("DESC")) {// sortの文字列にDESCが含まれる場合containsはListでしか使えない
			isDesc = true;	// DESC検索で設定する
		}		
		List<Item> itemList = itemRepository.findAllSort(sortArray[0], isDesc);
		return itemList;
	}
	
	public Item showDetail(Integer id) {
		Item item = itemRepository.findById(id);
		return item;
	}
	public List<Item> searchByNameAndSort(String name, String sort) {
		
		boolean isDesc = false;	// ASC検索で初期化しておく
		
		String[] sortArray = sort.split(" "); //文字列を分割split
		
		if (Arrays.asList(sortArray).contains("DESC")) {	// sortの文字列にDESCが含まれる場合（contains=要素を含むか）
			isDesc = true;	// DESC検索で設定する
		}	
		
		List<Item> itemList = itemRepository.findByNameAndSort(name, sortArray[0], isDesc);
		System.out.println("サービスのアイテムリスト：" + itemList);
		return itemList;
	}
}














