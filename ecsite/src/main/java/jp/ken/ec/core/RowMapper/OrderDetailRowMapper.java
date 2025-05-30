package jp.ken.ec.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.ec.domain.entity.OrderDetailEntity;

public class OrderDetailRowMapper implements RowMapper<OrderDetailEntity> {
	@Override
	public OrderDetailEntity mapRow(ResultSet rs, int rownum) throws SQLException{
		OrderDetailEntity order_detail = new OrderDetailEntity();
		order_detail.setOrder_detail_id(rs.getLong("order_detail_id"));
		order_detail.setOrder_id(rs.getLong("order_id"));
		order_detail.setProduct_id(rs.getLong("product_id"));
		order_detail.setQuantity(rs.getInt("quantity"));
		order_detail.setUnit_price(rs.getDouble("unit_price"));
		
		return order_detail;
		
	}
	

}
