package com.btcdo.demo.service.trade;

import com.btcdo.demo.pojo.OrderBtcdoVO;

public interface TradeService {
	OrderBtcdoVO getOrderById(Long orderId);

	OrderBtcdoVO createOrder(OrderBtcdoVO order);

	OrderBtcdoVO[] getOrders(long offsetId, int limit,String symbol);
}
