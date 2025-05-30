package jp.ken.ec.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ken.ec.application.service.ProductSelectService;
import jp.ken.ec.presentation.form.ProductForm;

@Controller
public class ProductController {
	private ProductSelectService p_select_service;
	
	public ProductController(ProductSelectService p_select_service) {
		this.p_select_service = p_select_service;
	}
	
	
	//一覧表示
	@GetMapping(value="/")
	String showTop(Model model) {
		try {
			List<ProductForm> products = p_select_service.get_all_product();
			model.addAttribute("products",products);
		}catch(Exception e) {
			model.addAttribute("error","商品の取得失敗");
			e.printStackTrace();
		}
		
		return "top";

	}
	
	//商品詳細
	@GetMapping(value="/product/{id}")
	public String show_detail(@PathVariable("id") Long id,Model model) {
		try {
		ProductForm product = p_select_service.get_product_id(id);
		model.addAttribute("product",product);
		}catch(Exception e) {
			model.addAttribute("error","商品が見つかりませんでした。");
		}
		return "product_details";
		
		
	}
	//商品検索
	@GetMapping("/search")
	public String search_product(@RequestParam(name = "query",required = false) String query,Model model) {
		List<ProductForm> products;
		try {
			products = p_select_service.search_products(query);
			model.addAttribute("products",products);
			model.addAttribute("query",query);
			
		} catch (Exception e) {
		
			model.addAttribute("error","商品が見つかりませんでした。");
		}
		return "search";
	}
	
	

}
