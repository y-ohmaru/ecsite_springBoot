package jp.ken.ec.domain.entity;

import java.util.List;

import lombok.Data;

@Data
public class OrderEntity {
	private Long order_id;
	private Long user_id;
	private String order_date;
	private Double total_amount;
	private String order_status;
	private String postal_code;
	private String delivery_address;
	private String customer_name;
	private String phone_number;
	
	
	//注文詳細リスト（マッピング用）
	private List<OrderDetailEntity> order_detail;

}
