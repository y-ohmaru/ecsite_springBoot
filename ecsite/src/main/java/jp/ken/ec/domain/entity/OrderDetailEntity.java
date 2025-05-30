package jp.ken.ec.domain.entity;

import lombok.Data;

@Data

public class OrderDetailEntity {
	//注文詳細ID
	private Long order_detail_id;
	//注文ID
	private Long order_id;
	//商品ID
	private Long product_id;
	//数量
	private Integer quantity;
	//単価
	private Double unit_price;
	

}
