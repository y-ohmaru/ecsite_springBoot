package jp.ken.ec.presentation.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryForm {
	@NotNull
	private Long category_id;
	
	@NotNull
	@Size(min=1,max=255)
	private String category_name;

}
