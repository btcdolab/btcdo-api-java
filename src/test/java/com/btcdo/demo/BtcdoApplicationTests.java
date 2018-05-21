package com.btcdo.demo;

import java.math.BigDecimal;
import java.util.Arrays;

import com.btcdo.demo.service.websocket.WebsocketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.btcdo.demo.enums.OrderType;
import com.btcdo.demo.pojo.OrderBtcdoVO;
import com.btcdo.demo.service.market.MarketService;
import com.btcdo.demo.service.trade.TradeService;
import com.btcdo.demo.service.user.UserService;

@SpringBootTest(classes = BtcdoAPIApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BtcdoApplicationTests extends AbstractTest {

	@Autowired
	MarketService marketService;
	@Autowired
	TradeService tradeService;
	@Autowired
	UserService userService;
	@Autowired
	WebsocketService websocketService;

	@Test
	public void test() {
		getPriceBySymbol();
		getOrderById(152650L);
		getOrders(0, 10, "BDB_ETH");
		createOrder();
		getAccount();
		getDepositLog();
		testWebsocket();
	}

	public void getPriceBySymbol() {
		logger.info("symbol {} price is:{}", "BDB_BTC", marketService.getPriceBySymbol("BDB_BTC").toString());
	}

	public void getOrderById(Long orderId) {
		logger.info("getOrderById resp is :{}", tradeService.getOrderById(orderId));
	}

	public void getOrders(long offsetId, int limit, String symbol) {
		logger.info("getOrders resp is :{}", Arrays.toString(tradeService.getOrders(offsetId, limit, symbol)));
	}

	public void createOrder() {
		OrderBtcdoVO order = new OrderBtcdoVO();
		order.setAmount(new BigDecimal("106"));
		order.setFeatures(65536);
		order.setPrice(new BigDecimal("0.00000669"));
		order.setSymbol("BDB_BTC");
		order.setType(OrderType.BUY_LIMIT);
		logger.info("createOrder resp is :{}", tradeService.createOrder(order).toString());
	}

	public void getAccount() {
		logger.info("getAccount resp is :{}", userService.getAccount());
	}

	public void getDepositLog() {
		logger.info("getDepositLog resp is :{}", userService.getDepositLog("BDB"));
	}

	public void testWebsocket(){
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("websocket resp is :{}", websocketService.getAllPrice().toString());
	}
}
