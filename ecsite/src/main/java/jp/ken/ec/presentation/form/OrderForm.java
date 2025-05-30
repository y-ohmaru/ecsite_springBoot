package jp.ken.ec.presentation.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderForm {
    @NotNull
    private String postal_code;
    
    @NotNull
    private String delivery_address;
    
    @NotNull
    @Size(min = 1, max = 255)
    private String customer_name;
    
    @NotNull
    @Pattern(regexp = "^[0-9\\-]+$", message = "電話番号は数字とハイフンで入力してください")
    private String phone_number;
}
