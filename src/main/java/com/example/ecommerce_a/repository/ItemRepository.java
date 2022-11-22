package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Item;

@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private final RowMapper<Item> ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(Item.class);

	private final String TABLE_NAME = "items";

	public List<Item> findAll() {
		String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY price_m DESC"; //M高い順デフォ
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	public List<Item> findAllSort(String sortName, boolean isDesc) {
//		System.out.println(sortName);
//		System.out.println(isDesc);
		String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + sortName;
		
		if (isDesc == true) { // DESCで検索する場合
			sql += " DESC";
		}
//		System.out.println(sql);
		
//		SqlParameterSource param = new MapSqlParameterSource().addValue("sort", sortName);
		SqlParameterSource param = new MapSqlParameterSource();
		List<Item> itemList = template.query(sql, param ,ITEM_ROW_MAPPER);
		return itemList;
	}

	
	public Item findById(Integer id) {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);

		return item;
	}

	public List<Item> findByNameAndSort(String name, String sortName, boolean isDesc) {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name like :name ORDER BY " + sortName;
		// SELECT * FROM items WHERE name like '%パーカー%' ORDER BY price_m DESC;

		if (isDesc == true) { // DESCで検索する場合
			sql += " DESC";
		}
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");

		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);

		System.out.println("リポジトリのアイテムリスト：" + itemList);

		return itemList;
	}
}
