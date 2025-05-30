package jp.ken.ec.domain.model;

import jp.ken.ec.presentation.form.ProductForm;
import lombok.Data;

@Data
public class CartItem {
    private Long productId;
    private int quantity;
    private Double unitPrice; // 単価
    private  ProductForm product_form;
    

    public CartItem(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    
}

