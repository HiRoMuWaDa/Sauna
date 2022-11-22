package com.example.ecommerce_a.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.OrderOption;

@Repository
public class OrderOptionRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	public void insert(OrderOption orderOption) {
		String sql = "INSERT INTO order_options (option_id,order_item_id) VALUES (:optionId,:orderItemId) ";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderOption);
		template.update(sql, param);
	}
}
