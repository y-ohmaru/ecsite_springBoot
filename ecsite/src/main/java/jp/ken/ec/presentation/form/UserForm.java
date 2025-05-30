package jp.ken.ec.presentation.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserForm {
	@NotNull
	private Long user_id; //主キー
	@NotNull
	@Size(min=3,max=50)
	private String username; //ユーザー名
	
	@NotNull
	@Email
	private String email; //メール
	@NotNull
	@Size(min=4,max=255)
	private String password; //パスワード

}
