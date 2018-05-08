package com.btcdo.demo.service.market.impl;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.btcdo.demo.service.AbstractService;
import com.btcdo.demo.service.market.MarketService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service("marketServiceBtcdo")
public class MarketServiceImpl extends AbstractService implements MarketService {

	@Value("${api.setting.btcdo.restApiEndpoint}")
	private String restApiEndpoint;

	@Override
	public BigDecimal getPriceBySymbol(String symbol) {
		String url = restApiEndpoint + "/v1/market/prices";
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().get().url(url)
				.addHeader("content-type", "application/json;charset:utf-8").build();
		Response response = null;
		BigDecimal price = null;
		try {
			response = client.newCall(request).execute();
			String prices = response.body().string();
			JSONObject pricesJSOB = JSONObject.parseObject(prices);
			JSONArray array = pricesJSOB.getJSONArray(symbol);
			price = (BigDecimal) array.get(4);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return price;
	}

}
