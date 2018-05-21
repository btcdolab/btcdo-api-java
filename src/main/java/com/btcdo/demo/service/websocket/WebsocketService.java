package com.btcdo.demo.service.websocket;

import java.math.BigDecimal;
import java.util.Map;

public interface WebsocketService {
    Map<String,BigDecimal> getAllPrice();
}
