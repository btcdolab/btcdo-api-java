package com.btcdo.demo.service.market;

import java.math.BigDecimal;

public interface MarketService {

	BigDecimal getPriceBySymbol(String symbol);
}
