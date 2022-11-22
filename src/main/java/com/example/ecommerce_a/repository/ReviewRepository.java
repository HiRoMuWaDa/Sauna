package com.example.ecommerce_a.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Review;

@Repository
public class ReviewRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 施設のレビューを新規追加するためのメソッドを定義
	 * @param review
	 * @return
	 */
	public Review insert(Review review) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(review);
		
//		if (review.getId() != null) {
//			throw new NullPointerException();
//		}
		template.update("insert into reviews (name, review, saunas_id) values (:name, :review, :saunasId)", param);
		return review;
	}
}
