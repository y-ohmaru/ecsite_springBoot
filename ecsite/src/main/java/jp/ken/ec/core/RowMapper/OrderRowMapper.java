package jp.ken.ec.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.ec.domain.entity.OrderEntity;

public class OrderRowMapper implements RowMapper<OrderEntity>{
	@Override
	public OrderEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
		OrderEntity order = new OrderEntity();
		
		order.setOrder_id(rs.getLong("order_id"));
		order.setUser_id(rs.getLong("user_id"));
		order.setOrder_date(rs.getString("order_date"));
		order.setTotal_amount(rs.getDouble("total_amount"));
		order.setOrder_status(rs.getString("order_status"));
		order.setPostal_code(rs.getString("postal_code"));
		order.setDelivery_address(rs.getString("delivery_address"));
		order.setPhone_number(rs.getString("phone_number"));
		order.setCustomer_name(rs.getString("customer_name"));
		
		return order;
		
		
	}
	
	

}
