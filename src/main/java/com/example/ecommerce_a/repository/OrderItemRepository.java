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

import com.example.ecommerce_a.domain.OrderItem;

@Repository
public class OrderItemRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	private static final RowMapper<OrderItem> ORDERITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(OrderItem.class);

	public List<OrderItem> findAllOrderItem(Integer orderId){
		String sql="SELECT * FROM order_items WHERE order_id=:orderId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId",orderId);
		List<OrderItem> orderItemList =template.query(sql, param,ORDERITEM_ROW_MAPPER);
		return orderItemList;
	}
	//sqlの読み込み　order_itemsから登録された商品の検索しOrderListに入れていく orderItemListで返す
	
	public void delete (Integer orderItemId) {
		
		String sql = "DELETE FROM order_items WHERE id = :orderItemId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId",orderItemId);
		template.update(sql, param);
	}
	//idから商品の削除
	
	public int insert(OrderItem orderItem) {
		String sql = "INSERT INTO order_items (item_id,order_id, quantity , size) VALUES (:itemId,:orderId,:quantity,:size) RETURNING id;";
		//"INSERT INTO order_items (item_id,order_id, quantity , size) VALUES (11,1,1,'M') ;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		
		int generatedOrderItemId = template.queryForObject(sql, param,Integer.class);
		System.out.println("generatedOrderItemId"+generatedOrderItemId);
		return generatedOrderItemId ;

	}
}
