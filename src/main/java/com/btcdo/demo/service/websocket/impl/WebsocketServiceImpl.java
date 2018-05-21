package com.btcdo.demo.service.websocket.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.btcdo.demo.service.AbstractService;
import com.btcdo.demo.service.websocket.WebsocketService;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service("websocketService")
public class WebsocketServiceImpl extends AbstractService implements WebsocketService {

    @Value("${websocket.uri}")
    private String WEBSOCKET_URI;

    @Override public Map<String,BigDecimal> getAllPrice(){
        return SYMBOL_PRICE;
    }


    @Bean
    public WebSocketClient initWebSocket()  throws Exception{
        WebSocketClient client = new WebSocketClient(new URI(WEBSOCKET_URI),new Draft_6455()) {

            @Override
            public void onOpen(ServerHandshake arg0) {
                logger.info("WebSocket打开链接");
            }

            @Override
            public void onMessage(String message) {
                logger.info("WebSocket收到消息"+message);
                if(message.contains("topic_prices")){//处理topic_prices消息
                    handleTopicPrice(message);
                }
            }

            @Override
            public void onError(Exception arg0) {
                logger.error("WebSocket发生错误已关闭",arg0);
            }

            @Override
            public void onClose(int arg0, String arg1, boolean arg2) {
                logger.warn("WebSocket链接已关闭");
            }

        };

        client.connect();

        while(!client.getReadyState().equals(org.java_websocket.WebSocket.READYSTATE.OPEN)){
            logger.info("WebSocket还没有打开");
        }
        logger.info("WebSocket打开了");
        return client;
    }

    private static Map<String,BigDecimal> SYMBOL_PRICE = new HashMap<>();
    private void handleTopicPrice(String message){
        message = message.replaceFirst("42\\[\"topic_prices\",","");
        message = message.substring(0,message.length()-1);
        JSONObject json = JSONObject.parseObject(message);
        for(String key : json.keySet()){
            JSONArray array = JSONArray.parseArray(json.getString(key));
            SYMBOL_PRICE.put(key,array.getBigDecimal(4));
        }
    }

}
