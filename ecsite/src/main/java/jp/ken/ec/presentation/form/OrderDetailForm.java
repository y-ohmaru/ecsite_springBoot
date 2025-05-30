package jp.ken.ec.presentation.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderDetailForm {
	@NotNull
	private Long order_detail_id;
	@NotNull
	private Long order_id;
	@NotNull
	private Long product_id;
	@NotNull
	@Min(1)
	private Integer quantity;
	@NotNull
	@Positive 
	private Double unit_price; //単価

}
