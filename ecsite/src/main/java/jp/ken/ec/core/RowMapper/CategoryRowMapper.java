package jp.ken.ec.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.ec.domain.entity.CategoryEntity;



public class CategoryRowMapper implements RowMapper<CategoryEntity> {
	@Override
	public CategoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
		CategoryEntity category_entity = new CategoryEntity();
		
		category_entity.setCategory_id(rs.getLong("category_id"));
		category_entity.setCategory_name(rs.getString("category_name"));
		
		return category_entity;
		
	}

}
