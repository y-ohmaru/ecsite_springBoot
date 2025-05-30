package jp.ken.ec.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.ec.domain.entity.ProductEntity;



public class ProductRowMapper implements RowMapper<ProductEntity> {
	@Override
	public ProductEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
		ProductEntity product = new ProductEntity();
		product.setProduct_id(rs.getLong("product_id"));
		product.setProduct_name(rs.getString("product_name"));
		product.setDescription(rs.getString("description"));
		product.setPrice(rs.getDouble("price"));
		product.setStock_quantity(rs.getInt("stock_quantity"));
		product.setImage_url(rs.getString("image_url"));
		product.setCategory_id(rs.getLong("category_id"));
		
		return product;
	}

}
