package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Option;

@Repository
public class OptionRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private final RowMapper<Option> OPTION_ROW_MAPPER = new BeanPropertyRowMapper<>(Option.class);

	private final String TABLE_NAME = "options";

	public List<Option> findAll() {
		String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY id";
		List<Option> optionList = template.query(sql, OPTION_ROW_MAPPER);

		return optionList;
	}
	
	public Option load(Integer id) {
		String sql = "SELECT * FROM " + TABLE_NAME + " where id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
		return template.queryForObject(sql, param, OPTION_ROW_MAPPER);
		
	}
	
}
