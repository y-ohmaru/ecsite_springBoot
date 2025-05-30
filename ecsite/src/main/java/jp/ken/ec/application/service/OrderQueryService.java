package jp.ken.ec.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.ken.ec.domain.entity.OrderDetailEntity;
import jp.ken.ec.domain.entity.OrderEntity;
import jp.ken.ec.domain.repository.OrderDetailRepository;
import jp.ken.ec.domain.repository.OrderRepository;

@Service
public class OrderQueryService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepoistory;
    
    public OrderQueryService(OrderRepository orderRepository,OrderDetailRepository orderDetailRepoistory) {
        this.orderRepository = orderRepository;
        this.orderDetailRepoistory = orderDetailRepoistory;
    }
    
    // ユーザーIDから最新の注文情報を取得（注文日時降順の最初の1件）
    public OrderEntity getLatestOrderByUserId(Long userId) {
        return orderRepository.find_last_byuser(userId);
    }
    
    /**
     * 注文IDに紐づく注文詳細を取得
     */
    public List<OrderDetailEntity> getOrderDetailsByOrderId(Long orderId) {
        return orderDetailRepoistory.findByOrderId(orderId);
    }
}
