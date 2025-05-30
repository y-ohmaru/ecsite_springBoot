package jp.ken.ec.application.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ken.ec.domain.entity.OrderDetailEntity;
import jp.ken.ec.domain.entity.OrderEntity;
import jp.ken.ec.domain.model.Cart;
import jp.ken.ec.domain.model.CartItem;
import jp.ken.ec.domain.repository.OrderDetailRepository;
import jp.ken.ec.domain.repository.OrderRepository;
import jp.ken.ec.presentation.form.OrderForm;

@Service
public class OrderInsertService {
	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final ModelMapper modelMapper;
	
	public OrderInsertService(OrderRepository orderRepository,ModelMapper modelMapper,OrderDetailRepository orderDetailRepository) {
		this.modelMapper= modelMapper;
		this.orderRepository = orderRepository;
		this.orderDetailRepository = orderDetailRepository;
	}
	
	
	@Transactional(rollbackFor=Exception.class)
	public int regist_order(OrderForm form,Double total_amount,Long user_id,Cart cart) throws Exception{
		//OrderForm から OrderEntity に変換
		OrderEntity order = modelMapper.map(form,OrderEntity.class);
		
        order.setUser_id(user_id);
        order.setOrder_date(LocalDateTime.now().toString());
        order.setTotal_amount(total_amount);
        order.setOrder_status("PENDING");
        
     // 注文情報をDBに登録し、生成された order_id を取得
        int orderId = orderRepository.register(order);
        if (orderId <= 0) {
            throw new Exception("注文の登録に失敗しました。");
        }
     // カート内の商品情報を注文詳細として登録
        List<CartItem> cartItems = cart.getItems();
        if (cartItems == null || cartItems.isEmpty()) {
            throw new Exception("カートが空のため、注文詳細を登録できません。");
        }
        
        for (CartItem item : cartItems) {
            OrderDetailEntity orderDetail = new OrderDetailEntity();
            orderDetail.setOrder_id((long) orderId);
            orderDetail.setProduct_id(item.getProductId());
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setUnit_price(item.getProduct_form().getPrice());

            // 注文詳細を登録
            orderDetailRepository.save(orderDetail);
        }

        return orderId;
    }
	
	
	

}
