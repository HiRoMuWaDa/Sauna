package com.example.ecommerce_a.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Option;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderOption;


@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

//	private final RowMapper<Order> ORDER_ROW_MAPPER = new BeanPropertyRowMapper<>(Order.class);

	private final ResultSetExtractor<List<Order>> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {
		Map<Integer, Order> map = new LinkedHashMap<>();
		Map<Integer, OrderItem> mapOi = new LinkedHashMap<>();
		Order order = null;
		OrderItem orderItem = null;

		while (rs.next()) {
			Integer orderId = rs.getInt("order_id");

			order = map.get(orderId);

			if (order == null) {
				order = new Order();
				order.setId(orderId);
				order.setUserId(rs.getInt("user_id"));
				order.setStatus(rs.getInt("status"));
				order.setTotalPrice(rs.getInt("total_price"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setDestinationName(rs.getString("destination_name"));
				order.setDestinationEmail(rs.getString("destination_email"));
				order.setDestinationZipcode(rs.getString("destination_zipcode"));
				order.setDestinationAddress(rs.getString("destination_address"));
				order.setDeliveryTime(rs.getTimestamp("delivery_time"));
				order.setPaymentMethod(rs.getInt("payment_method"));
				List<OrderItem> orderItemList = new ArrayList<>();
				mapOi = new LinkedHashMap<>();
				order.setOrderItemList(orderItemList);
				map.put(orderId, order);
			}

			Integer orderItemId = rs.getInt("oi_id");
			orderItem = mapOi.get(orderItemId);

			if (orderItem == null) {
				if (orderItemId != 0) {
					orderItem = new OrderItem();
					orderItem.setId(orderItemId);
					orderItem.setItemId(rs.getInt("oi_item_id"));
					orderItem.setOrderId(rs.getInt("oi_order_id"));
					orderItem.setQuantity(rs.getInt("quantity"));
					orderItem.setSize((rs.getString("size").toCharArray()[0]));
					Item item = new Item();
					item.setId(rs.getInt("i_id"));
					item.setName(rs.getString("i_name"));
					item.setDescription(rs.getString("description"));
					item.setPriceS(rs.getInt("price_s"));
					item.setPriceM(rs.getInt("price_m"));
					item.setPriceL(rs.getInt("price_l"));
					item.setImagePath(rs.getString("image_path"));
					item.setDeleted(rs.getBoolean("deleted"));
					orderItem.setItem(item);

					List<OrderOption> orderOptionList = new ArrayList<>();
					orderItem.setOrderOptionList(orderOptionList);
					mapOi.put(orderItemId, orderItem);
				}
			}

			Integer orderOptionId = rs.getInt("oo_id");

			if (orderOptionId != 0) {
				OrderOption orderOption = new OrderOption();
				orderOption.setOptionId(rs.getInt("oo_option_id"));
				orderOption.setOrderItemId(rs.getInt("oo_order_item_id"));
				Option option = new Option();
				option.setId(rs.getInt("op_id"));
				option.setName(rs.getString("op_name"));
				option.setPrice(rs.getInt("op_price"));
				orderOption.setOption(option);

				orderItem.getOrderOptionList().add(orderOption);
			}
			
			
			order.setOrderItemList(new ArrayList<OrderItem>(mapOi.values())); 
			
		}
		return new ArrayList<Order>(map.values());

	};

	public List<Order> findOrderHistory(Integer userId) {
		String sql = "select " + "o.id AS order_id , o.user_id , o.status ,o.total_price , o.order_date ,"
				+ "o.destination_name,o.destination_email,o.destination_zipcode,o.destination_address,o.destination_tel,o.delivery_time,o.payment_method,"
				+ "oi.id AS oi_id , oi.item_id AS oi_item_id , oi.order_id AS oi_order_id, oi.quantity , oi.size,"
				+ "i.id AS i_id , i.name AS i_name, i.description , i.price_s ,i.price_m ,i.price_l ,i.image_path, i.deleted ,"
				+ "oo.id AS oo_id ,oo.option_id AS oo_option_id , oo.order_item_id AS oo_order_item_id ,"
				+ "op.id AS op_id ,op.name AS op_name , op.price AS op_price " + "FROM orders AS o "
				+ "LEFT JOIN order_items as oi ON o.id = oi.order_id " + "LEFT JOIN items as i on oi.item_id = i.id "
				+ "LEFT JOIN order_options as oo ON oi.id = oo.order_item_id "
				+ "LEFT JOIN options as op ON oo.option_id = op.id "
				+ "WHERE o.user_id =  :userId and o.status != 0 ORDER BY o.order_date DESC;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		return template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
//destination→
	}
	
	
	
	public List<Order> findByUserIdAndStatus(Integer userId, Integer status) {
		String sql = "select " + "o.id AS order_id , o.user_id , o.status ,o.total_price , o.order_date ,"
				+ "o.destination_name,o.destination_email,o.destination_zipcode,o.destination_address,o.destination_tel,o.delivery_time,o.payment_method,"
				+ "oi.id AS oi_id , oi.item_id AS oi_item_id , oi.order_id AS oi_order_id, oi.quantity , oi.size,"
				+ "i.id AS i_id , i.name AS i_name, i.description , i.price_s ,i.price_m ,i.price_l ,i.image_path, i.deleted ,"
				+ "oo.id AS oo_id ,oo.option_id AS oo_option_id , oo.order_item_id AS oo_order_item_id ,"
				+ "op.id AS op_id ,op.name AS op_name , op.price AS op_price " + "FROM orders AS o "
				+ "LEFT JOIN order_items as oi ON o.id = oi.order_id " + "LEFT JOIN items as i on oi.item_id = i.id "
				+ "LEFT JOIN order_options as oo ON oi.id = oo.order_item_id "
				+ "LEFT JOIN options as op ON oo.option_id = op.id "
				+ "WHERE o.user_id =  :userId and o.status = :status;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		return template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);

	}

	public void updateTotalPrice(Integer orderId , Integer totalPrice) {
		String sql = "UPDATE orders SET total_price = :totalPrice WHERE id = :orderId ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId).addValue("totalPrice", totalPrice);
		template.update(sql, param);
	}

	public int insert(Order order) {
		String sql = "INSERT INTO orders ( user_id, total_price) VALUES (:userId, 0) RETURNING id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId",order.getUserId());
		 int insertedOrderId =  template.queryForObject(sql, param,Integer.class);
//		 System.out.println("insertedOrderId"+insertedOrderId);
		 return insertedOrderId;
	}

	/**
	 * 注文情報を更新する
	 * @param order
	 * @return
	 */
	public Order save(Order order) {
//		System.out.println(order);
		String sql = "UPDATE orders SET status=:status, order_date=:orderDate, destination_name=:destinationName, destination_email=:destinationEmail, destination_zipcode=:destinationZipcode,"
				+ " destination_address=:destinationAddress, destination_tel=:destinationTel, delivery_time=:deliveryTime, payment_method=:paymentMethod WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("status", order.getStatus()).addValue("orderDate", order.getOrderDate()).addValue("destinationName", order.getDestinationName()).addValue("destinationEmail", order.getDestinationName())
				 .addValue("destinationZipcode", order.getDestinationZipcode()).addValue("destinationAddress", order.getDestinationAddress()).addValue("destinationTel", order.getDestinationTel()).addValue("deliveryTime", order.getDeliveryTime()).addValue("paymentMethod", order.getPaymentMethod()).addValue("id", order.getId());
		template.update(sql, param);
		return order;

	}
}
