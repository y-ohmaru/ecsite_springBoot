package jp.ken.ec.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.ec.core.RowMapper.OrderRowMapper;
import jp.ken.ec.domain.entity.OrderEntity;

@Repository
public class OrderRepository {
	private final JdbcTemplate jdbcTemplate;
	private RowMapper<OrderEntity> order_mapper = new OrderRowMapper();
	
	public OrderRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<OrderEntity> get_all_order(){
		 String sql = "SELECT order_id, user_id, order_date, total_amount, order_status FROM orders ORDER BY order_id";
		 
		 List<OrderEntity> order_entity = jdbcTemplate.query(sql, order_mapper);
		 return order_entity;
		
	}
	
	//注文情報の登録
	public int register(OrderEntity order) {
		String sql = "INSERT INTO orders (user_id, order_date, total_amount, order_status, postal_code, delivery_address, customer_name, phone_number) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
					 jdbcTemplate.update(sql,order.getUser_id()
											,order.getOrder_date()
											,order.getTotal_amount()
											,order.getOrder_status()
											,order.getPostal_code()
											,order.getDelivery_address()
											,order.getCustomer_name()
											,order.getPhone_number());
		
		//最後に挿入されたorder_id取得
		String selectSql = "SELECT LAST_INSERT_ID()";
		Integer orderId = jdbcTemplate.queryForObject(selectSql, Integer.class);
		return orderId;
		
		
	}
	
	public OrderEntity find_last_byuser(Long user_id) {
		
	    String sql = "SELECT order_id, user_id, order_date, total_amount, order_status, postal_code, delivery_address, customer_name, phone_number " +
	                 "FROM orders WHERE user_id = ? ORDER BY order_date DESC LIMIT 1";
	    OrderEntity entity = null;
	    entity = jdbcTemplate.queryForObject(sql, order_mapper, user_id);
	    
	    return entity;
	}

	
	
	
}
