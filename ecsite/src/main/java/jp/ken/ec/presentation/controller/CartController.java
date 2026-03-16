package jp.ken.ec.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jp.ken.ec.application.service.ProductSelectService;
import jp.ken.ec.domain.model.Cart;
import jp.ken.ec.domain.model.CartItem;
import jp.ken.ec.presentation.form.ProductForm;

@Controller
public class CartController {
	
	private final ProductSelectService product_service;
	
	public CartController(ProductSelectService product_service) {
		this.product_service = product_service;
	}
	
	
	//カートに商品追加
	@PostMapping("/cart/add")
	public String add_cart(@RequestParam("product_id") Long product_id,@RequestParam(value = "quantity",defaultValue ="1")
							int quantity, HttpSession session ,RedirectAttributes redirectAttributes) throws Exception {

		//サービスクラス利用して商品詳細情報を ProductFormとして取得
		ProductForm product_form = product_service.get_product_id(product_id);
		if(product_form == null) {
			//エラーページにリダイレクト予定
			return "redirect:/";
			
		}
		
		//修正コード追加
		//在庫チェック
		int stock = product_form.getStock_quantity();
		if(stock <= 0) {
			redirectAttributes.addFlashAttribute("error","この商品は在庫切れです");
			return "redirect:/product/" + product_id;
		}
		
		
		//カート内の既存数量+今回の数量が在庫を超えないかチェック
		//セッションからカート取得、なければ新規作成
		Cart cart = (Cart)session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		
		int currentQuantity = cart.getItems().stream()
				.filter(item -> item.getProductId().equals(product_id))
				.mapToInt(CartItem::getQuantity)
				.findFirst()
				.orElse(0);
		if(currentQuantity + quantity > stock) {
			redirectAttributes.addFlashAttribute("error", "在庫数を超えて追加出来ません。(残り在庫"+(stock - currentQuantity) +"個）");
			return "redirect:/product/"+product_id;
		}
		
		CartItem new_item = new CartItem(product_id,quantity);
		new_item.setProduct_form(product_form);
		cart.addItem(new_item);
		
		return "redirect:/product/"+product_id;
	
	}
	
	//カートの中身を表示
	@GetMapping("/cart")
	public String show_cart(HttpSession session,Model model) {
		//セッションからカート取得
		Cart cart = (Cart)session.getAttribute("cart");
		model.addAttribute("cart",cart);
		return "cart";
	}
	
	
	
	//カート個別削除
	@PostMapping("/cart/remove")
	public String remove_cart_item(@RequestParam("product_id")Long product_id, HttpSession session) {
		Cart cart = (Cart)session.getAttribute("cart");
		
		if(cart != null) {
			cart.remove_item(product_id);
		}
		return "redirect:/cart";
		
	}
	
	//カート全体削除
	@PostMapping("/cart/clear")
	public String clear_cart(HttpSession session) {
		Cart cart = (Cart)session.getAttribute("cart");
		if(cart != null) {
			cart.clear_cart();
		}
		
		return "redirect:/cart";
	}
	
	//購入画面に遷移
	@GetMapping("/purchase/confirm")
	public String confirm_purchase(HttpSession session ,Model model) {
		Cart cart = (Cart)session.getAttribute("cart");
		if(cart == null || cart.getItems().isEmpty()) {
			model.addAttribute("message","カートは空です");
			return "purchase_confirm";
		}
		
		model.addAttribute("cart", cart);
		model.addAttribute("total_amount",cart.getTotal_amount());
		
		return "purchase_confirm";
	}
}

