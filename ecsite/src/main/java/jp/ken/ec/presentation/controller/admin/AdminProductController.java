package jp.ken.ec.presentation.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.ken.ec.application.service.ProductSelectService;
import jp.ken.ec.presentation.form.ProductForm;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
	private ProductSelectService p_select_service;
	
	public AdminProductController(ProductSelectService p_select_service) {
		this.p_select_service = p_select_service;
	}
	
	//管理者用商品一覧
	@GetMapping
	public String list(Model model) throws Exception {
		
		model.addAttribute("products",p_select_service.get_all_product());
		return "admin/product_list";
	}
	
	//商品新規登録画面
	@GetMapping("/create")
	public String createProduct(Model model){
		
		model.addAttribute("productForm",new ProductForm());
		return "admin/product_form";
	}
	
	//商品更新（Edit）画面
	@GetMapping("/edit/{id}")
	public String editProduct(@PathVariable long id){
		
		return "admin/product_form";
	}
	
	//商品削除画面
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable long id){
		
		return "redirect:/admin/products";
	}

}
