package com.btcdo.demo.service.trade.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.btcdo.demo.enums.OrderType;
import com.btcdo.demo.pojo.OrderBeanReqDto;
import com.btcdo.demo.pojo.OrderBtcdoVO;
import com.btcdo.demo.service.AbstractService;
import com.btcdo.demo.service.trade.TradeService;
import com.fasterxml.jackson.core.type.TypeReference;

@Service("btcdoTradeService")
public class TradeServiceImpl extends AbstractService implements TradeService {

	@Override
	public OrderBtcdoVO getOrderById(Long orderId) {
		OrderBtcdoVO order = client.getRestClientSign().get(new TypeReference<OrderBtcdoVO>() {
		}, "/v1/trade/orders/" + orderId, null);
		logger.info("btcdoTradeService getOrderById is {}", order.toString());
		return order;
	}

	@Override
	public OrderBtcdoVO createOrder(OrderBtcdoVO order) {
		OrderBeanReqDto body = new OrderBeanReqDto();
		body.setOrderType(order.getType());
		body.setSymbol(order.getSymbol());

		if (OrderType.CANCEL_BUY_LIMIT.equals(order.getType()) || OrderType.CANCEL_SELL_LIMIT.equals(order.getType())) {
			body.setTargetOrderId(order.getTargetOrderId());
		} else {
			body.setAmount(order.getAmount());
			body.setPrice(order.getPrice());
			body.setCustomFeatures(65536);
		}

		OrderBtcdoVO respOrder = client.getRestClientSign().post(new TypeReference<OrderBtcdoVO>() {
		}, "/v1/trade/orders/", body);
		return respOrder;
	}

	@Override
	public OrderBtcdoVO[] getOrders(long offsetId, int limit, String symbol) {
		Map<String, String> inputMap = new HashMap<String, String>();
		inputMap.put("offsetId", Long.toString(offsetId));
		inputMap.put("limit", Integer.toString(limit));
		inputMap.put("symbol", symbol);
		Map<String, OrderBtcdoVO[]> order = client.getRestClientSign()
				.get(new TypeReference<Map<String, OrderBtcdoVO[]>>() {
				}, "/v1/trade/orders", inputMap);
		return order.get("orders");
	}

}
