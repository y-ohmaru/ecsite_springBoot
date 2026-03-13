package jp.ken.ec.domain.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.ec.core.RowMapper.UserRowMapper;
import jp.ken.ec.domain.entity.UserEntity;

@Repository
public class UserRepository {
	private final JdbcTemplate jdbcTemplate;
	private RowMapper<UserEntity> user_mapper = new UserRowMapper();
	
	public UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}
	
	public List<UserEntity> get_all_user() throws Exception{
		String sql = "SELECT user_id, username, email, password, role, created_at, updated_at FROM users ORDER BY user_id";
		List<UserEntity> user_entity = jdbcTemplate.query(sql,user_mapper );
		
		return user_entity;
	}
	
	
	 public UserEntity findByUsername(String username) {
	        String sql = "SELECT user_id, username, email, password, role, created_at, updated_at FROM users WHERE username = ?";
	        try {
	            return jdbcTemplate.queryForObject(sql, user_mapper, username);
	        } catch (EmptyResultDataAccessException e) {
	            // 該当ユーザーが存在しない場合は null を返す（必要に応じて例外をスローしても良い）
	            return null;
	        }
	    }
	

}
