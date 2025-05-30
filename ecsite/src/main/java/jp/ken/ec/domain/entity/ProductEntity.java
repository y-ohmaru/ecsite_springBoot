package jp.ken.ec.domain.entity;

import lombok.Data;

@Data
public class ProductEntity {
	//主キー
	private Long product_id;
	//商品名
	private String product_name;
	//商品説明
	private String description;
	//金額
	private Double price;
	//在庫数
	private Integer stock_quantity;
	//商品画像
	private String image_url;
	//カテゴリ－ID
	private Long category_id;
	

}
