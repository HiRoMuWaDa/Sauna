package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.User;

@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private final RowMapper<User> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);

	private final String TABLE_NAME = "users";

	public void insert(User user) {
		String sql = "INSERT INTO " + TABLE_NAME
				+ " (name, email, password, zipcode, address, telephone) values(:name, :email, :password, :zipcode, :address, :telephone);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);

		template.update(sql, param);
	}

	public List<User> findByMailAddress(String email) {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE email = :email ;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);

		return template.query(sql, param, USER_ROW_MAPPER);
	}

	/**
	 * ログイン時にメールアドレスとパスワードが一致しているか確認するためのメソッド
	 * 
	 * @param email
	 * @param password
	 * @return メールアドレスとパスワードが一致した施設を取得する
	 */

	public User findByEmailAndPassword(String email, String password) {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE email=:email AND password=:password";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);

		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	public User load(Integer id) {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		User user = template.queryForObject(sql, param, USER_ROW_MAPPER);
		return user;
	}

	/**
	 * コメント後のポイントを更新するメソッド
	 * 
	 * @param user
	 * @return user
	 */
	public User updatePoint(User user) {
		String updateSpl = "UPDATE " + TABLE_NAME + " SET point=:point WHERE id=:id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(updateSpl, param);
		
		
		return load(user.getId());
	}

	
}
