package jp.ken.ec.application.service;

import jp.ken.ec.domain.entity.OrderDetailEntity;
import jp.ken.ec.domain.repository.OrderDetailRepository;

public class OrderDetailService {
	 private final OrderDetailRepository orderDetailRepository;

	    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
	        this.orderDetailRepository = orderDetailRepository;
	    }

	    /**
	     * 注文詳細を登録する
	     */
	    public void saveOrderDetail(OrderDetailEntity orderDetail) {
	        orderDetailRepository.save(orderDetail);
	    }

}
