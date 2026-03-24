package jp.ken.ec.presentation.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.ken.ec.application.service.OrderInsertService;
import jp.ken.ec.application.service.OrderQueryService;
import jp.ken.ec.common.security.CustomUserDetails;
import jp.ken.ec.domain.entity.OrderDetailEntity;
import jp.ken.ec.domain.entity.OrderEntity;
import jp.ken.ec.domain.model.Cart;
import jp.ken.ec.presentation.form.OrderForm;

@Controller
public class OrderContoller {
	private final OrderInsertService orderInsertService;
	private final OrderQueryService  orderQueryService;
	
	public OrderContoller(OrderInsertService orderInsertService,OrderQueryService  orderQueryService){
		this.orderInsertService = orderInsertService;
		this.orderQueryService =  orderQueryService;
	}
	
	
	
	@GetMapping("/order/address")
	public String oder_address(Model model, HttpSession session) {
		model.addAttribute("order_form", new OrderForm());
		
		Cart cart = (Cart)session.getAttribute("cart");
		model.addAttribute(cart);
		
		if (cart != null) {
			model.addAttribute("total_amount",cart.getTotal_amount());
		}else {
			model.addAttribute("total_amount",0);
		
		}
		return "order_address";
		
	}
	
	// POST: 住所入力フォーム送信後、配送先住所と購入商品を確認する画面へ遷移
	@PostMapping("/order/address")
	public String order_address_form(@Valid @ModelAttribute("order_form") OrderForm order_form,
			BindingResult bindingResult,Model model,HttpSession session) {
		if(bindingResult.hasErrors()) {
			//入力にエラーある場合、再度入力させる。
			Cart cart = (Cart)session.getAttribute("cart");
			model.addAttribute("cart", cart);
			if(cart != null) {
				model.addAttribute("total_amount",cart.getTotal_amount());
			}else {
				model.addAttribute("total_amount",0);
			}
			return "order_address";
		}
		model.addAttribute("address", order_form);
		
		//セッションからカート情報を取得
		Cart cart = (Cart)session.getAttribute("cart");
		model.addAttribute("cart",cart);
		
		if(cart != null) {
			model.addAttribute("total_amount", cart.getTotal_amount());
		}else {
			model.addAttribute("total_amount", 0);
		}
		
		
		//住所+商品確認画面
		return "order_address_confirm";
	}
	
	
	//購入確定処理
	@PostMapping("/order/confirm")
	public String confirm_order(@ModelAttribute("address") OrderForm order_form,Model model,HttpSession session) {
		
		//ログイン中のユーザー情報をSecurityContextHolder から取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Long user_id = null;
		
		if(auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails user_details = (CustomUserDetails)auth.getPrincipal();
			user_id = user_details.getUserId();
		}
		
		if(user_id == null) {
			//ログインしていない場合はログイン画面へ
			return "redirect:/login";
		}

		//セッションからカート情報を取得
		Cart cart = (Cart)session.getAttribute("cart");
		if(cart == null || cart.getItems().isEmpty()) {
			model.addAttribute("error","カートに商品がありません");
			return "cart";
		}
		
		Double total_amount = cart.getTotal_amount();
		
		try {
			//注文情報の登録
			int result = orderInsertService.regist_order(order_form, total_amount, user_id,cart);
			System.out.println("注文登録成功: orderId = " + result);
			if(result > 0) {
				//成功時はセッションからカート削除
				session.removeAttribute("cart");
				//この部分でDBから注文情報取得したい。
				 OrderEntity orderEntity = orderQueryService.getLatestOrderByUserId(user_id);
				 List<OrderDetailEntity> orderDetails = orderQueryService.getOrderDetailsByOrderId(orderEntity.getOrder_id());
				 model.addAttribute("order", orderEntity);
				 model.addAttribute("orderDetails", orderDetails);
				
				return "complete";
			}else {
				model.addAttribute("error","注文情報の登録に失敗しました。");
				return "order_address_confirm";
			}
		}catch(Exception e){
			model.addAttribute("error", "注文登録エラー：" + e.getMessage());
			return "order_address_confirm";
			
		}
		
	}
	

}
