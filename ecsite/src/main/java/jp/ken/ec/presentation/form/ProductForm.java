package jp.ken.ec.presentation.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class ProductForm {
	@NotNull
	private Long product_id;
	
	@NotNull
	@Size(min = 1, max=255)
	private String product_name;
	
	@Size(max=500)
	private String description; //商品説明
	
	@NotNull
	@Positive
	private Double price;//金額
	@NotNull
	@Min(0)
	private Integer stock_quantity;//在庫数
	
	private String image_url;//商品画像
	
	@NotNull
	private Long category_id;//カテゴリ－ID
	

}
