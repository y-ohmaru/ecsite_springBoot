package jp.ken.ec.domain.entity;

import lombok.Data;

@Data

public class UserEntity {
	//主キー
	private Long user_id;
	//ユーザー名
	private String username;
	//メール
	private String email;
	//パスワード
	private String password;
	//ロール
	private String role;
	// 作成日時（DBカラム名と一致）
	private String created_at; 
	// 更新日時（DBカラム名と一致）
    private String updated_at; 
	
	
	

}
