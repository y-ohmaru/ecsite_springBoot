package jp.ken.ec.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.ec.core.RowMapper.ProductRowMapper;
import jp.ken.ec.domain.entity.ProductEntity;

@Repository
public class ProductRepository {

    private final RowMapper<ProductEntity> product_mapper = new ProductRowMapper();
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductEntity> get_all_product() throws Exception {
        String sql = "SELECT product_id, product_name, description, price, stock_quantity, image_url, category_id FROM products ORDER BY product_id";
        return jdbcTemplate.query(sql, product_mapper);
    }

    public ProductEntity get_product_id(Long product_id) throws Exception {
        String sql = "SELECT product_id, product_name, description, price, stock_quantity, image_url, category_id FROM products WHERE product_id = ?";
        return jdbcTemplate.queryForObject(sql, product_mapper, product_id);
    }

    public List<ProductEntity> search_product(String keyword) throws Exception {
        String sql = "SELECT product_id, product_name, description, price, stock_quantity, image_url, category_id " +
                     "FROM products WHERE LOWER(product_name) LIKE ? OR LOWER(description) LIKE ? ORDER BY product_id";
        String like_keyword = "%" + keyword.toLowerCase() + "%";
        return jdbcTemplate.query(sql, product_mapper, like_keyword, like_keyword);
    }

    // 管理者用：商品登録
    public int save(ProductEntity product) {
        String sql = "INSERT INTO products (product_name, description, price, stock_quantity, image_url, category_id) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                product.getProduct_name(), product.getDescription(), product.getPrice(),
                product.getStock_quantity(), product.getImage_url(), product.getCategory_id());
    }

    // 管理者用：商品更新
    public int update(ProductEntity product) {
        String sql = "UPDATE products SET product_name = ?, description = ?, price = ?, stock_quantity = ?, image_url = ?, category_id = ? WHERE product_id = ?";
        return jdbcTemplate.update(sql,
                product.getProduct_name(), product.getDescription(), product.getPrice(),
                product.getStock_quantity(), product.getImage_url(), product.getCategory_id(),
                product.getProduct_id());
    }

    // 管理者用：商品削除
    public int delete(Long product_id) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        return jdbcTemplate.update(sql, product_id);
    }
}