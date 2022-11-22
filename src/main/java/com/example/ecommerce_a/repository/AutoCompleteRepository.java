package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AutoCompleteRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<String> NAME_ROW_MAPPER = (rs, i) -> {
		String name = rs.getString("name");
		return name;
	};

	public List<String> getAllNames() {
		List<String> allNames = template.query("SELECT name FROM items", NAME_ROW_MAPPER);
		return allNames;
	}
}
