package jp.ken.ec.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.ec.domain.entity.UserEntity;

public class UserRowMapper implements RowMapper<UserEntity>{
	@Override
	public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
		UserEntity user =new UserEntity();
		user.setUser_id(rs.getLong("user_id"));
		user.setUsername(rs.getString("username"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setRole(rs.getString("role"));
		user.setCreated_at(rs.getString("created_at"));
		user.setUpdated_at(rs.getString("updated_at"));
		
		return user;
		
	}
	

}
