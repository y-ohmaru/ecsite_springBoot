package jp.ken.ec.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.ec.core.RowMapper.OrderDetailRowMapper;
import jp.ken.ec.domain.entity.OrderDetailEntity;

@Repository

public class OrderDetailRepository {
	private final JdbcTemplate jdbcTemplate;
	private RowMapper<OrderDetailEntity> order_mapper;
	
	public OrderDetailRepository (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.order_mapper = new OrderDetailRowMapper();
	}
	
	List<OrderDetailEntity> get_all_detail() throws Exception{
		String sql = "SELECT order_detail_id, order_id, product_id, quantity, unit_price FROM order_details ORDER BY order_detail_id";
		List<OrderDetailEntity> order_detail_entity = jdbcTemplate.query(sql, order_mapper);
		
		return order_detail_entity;
	}
	
	public List<OrderDetailEntity> findByOrderId(long orderId) {
        String sql = "SELECT order_detail_id, order_id, product_id, quantity, unit_price FROM order_details WHERE order_id = ?";
        return jdbcTemplate.query(sql, order_mapper, orderId);
    }
	
	public int save(OrderDetailEntity orderDetail) {
        String sql = "INSERT INTO order_details (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        
        return jdbcTemplate.update(sql,
                orderDetail.getOrder_id(),
                orderDetail.getProduct_id(),
                orderDetail.getQuantity(),
                orderDetail.getUnit_price());
    }

}
