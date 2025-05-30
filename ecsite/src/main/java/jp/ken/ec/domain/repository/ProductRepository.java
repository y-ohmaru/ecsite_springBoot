package jp.ken.ec.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.ec.core.RowMapper.ProductRowMapper;
import jp.ken.ec.domain.entity.ProductEntity;


@Repository
public class ProductRepository {
	private RowMapper<ProductEntity> product_mapper  = new ProductRowMapper();
	private final JdbcTemplate jdbcTemplate;
	
	
	public ProductRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
		
	}
	//商品一覧
	public List<ProductEntity> get_all_product() throws Exception{
		String sql =  "SELECT product_id, product_name, description, price, stock_quantity, image_url, category_id FROM products ORDER BY product_id";
		
		List<ProductEntity> product_entity = jdbcTemplate.query(sql, product_mapper);
		return product_entity;
		
	}
	//商品IDをもとに一件取得
	public ProductEntity get_product_id(Long product_id) throws Exception{
		String sql = "SELECT product_id, product_name, description, price, stock_quantity, image_url, category_id FROM products WHERE product_id = ?";
		ProductEntity  product_entity = jdbcTemplate.queryForObject(sql, product_mapper, product_id);
		
		return product_entity;
	}
	
	//商品キーワード検索　大文字　小文字の違い無視
	public List<ProductEntity> search_product(String keyword) throws Exception{
		String sql = "SELECT product_id, product_name, description, price, stock_quantity, image_url, category_id " +
					 "FROM products " +
					 "WHERE LOWER(product_name) LIKE ? OR LOWER(description) LIKE ? " +
					 "ORDER BY product_id";
		
		//キーワード小文字に変換し、前後にワイルドカードを付与。
		String like_keyword = "%" +  keyword.toLowerCase() + "%";
		//商品名と説明文で検索させるためlike_keywordを２箇所指定。
		List<ProductEntity> product_list = jdbcTemplate.query(sql, product_mapper, like_keyword,like_keyword);
		
		return product_list;
				
	}
	
	
	

}
