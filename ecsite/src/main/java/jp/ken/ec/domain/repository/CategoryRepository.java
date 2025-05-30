package jp.ken.ec.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.ec.core.RowMapper.CategoryRowMapper;
import jp.ken.ec.domain.entity.CategoryEntity;

@Repository

public class CategoryRepository {
	private RowMapper<CategoryEntity> category_mapper = new CategoryRowMapper();
	private final JdbcTemplate jdbcTemplate;
	
	public CategoryRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		
	}
	
	public List<CategoryEntity> get_all_categories() throws Exception{
		String sql = "SELECT category_id, category_name FROM categories ORDER BY category_id";
		List<CategoryEntity> categories_list = jdbcTemplate.query(sql, category_mapper);
		
		return categories_list;
			
	}
	

}
