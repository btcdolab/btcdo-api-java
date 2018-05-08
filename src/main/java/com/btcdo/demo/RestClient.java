package com.btcdo.demo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.btcdo.demo.exception.ApiException;
import com.btcdo.demo.exception.ApiException.ApiErrorResponse;
import com.btcdo.demo.util.HashUtil;
import com.btcdo.demo.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Http client for accessing crypto exchange REST APIs.
 * 
 */
public class RestClient {

	final Logger logger = LoggerFactory.getLogger(getClass());

	final String endpoint;
	final String host;
	final String apiKey;
	final String apiSecret;

	OkHttpClient client;
	Request lastRequest = null;

	public static class Builder {

		final Logger logger = LoggerFactory.getLogger(getClass());

		String scheme;
		String host;
		int port;
		String apiKey;
		String apiSecret;

		int connectTimeout = 30;
		int readTimeout = 30;
		int keepAlive = 30;

		/**
		 * Create builder with api endpoint. e.g. "https://bit.itranswarp.com:8080".
		 * NOTE: do not append any PATH.
		 * 
		 * @param apiEndpoint
		 *            The api endpoint.
		 */
		public Builder(String apiEndpoint) {
			logger.info("build RestClient from {}...", apiEndpoint);
			try {
				URI uri = new URI(apiEndpoint);
				if (!"https".equals(uri.getScheme()) && !"http".equals(uri.getScheme())) {
					throw new IllegalArgumentException("Invalid API endpoint: " + apiEndpoint);
				}
				if (uri.getPath() != null && !uri.getPath().isEmpty()) {
					throw new IllegalArgumentException("Invalid API endpoint: " + apiEndpoint);
				}
				this.scheme = uri.getScheme();
				this.host = uri.getHost().toLowerCase();
				this.port = uri.getPort();
			} catch (URISyntaxException e) {
				throw new IllegalArgumentException("Invalid API endpoint: " + apiEndpoint, e);
			}
		}

		public Builder authenticate(String apiKey, String apiSecret) {
			this.apiKey = apiKey;
			this.apiSecret = apiSecret;
			return this;
		}

		public Builder connectTimeout(int connectTimeoutInSeconds) {
			this.connectTimeout = connectTimeoutInSeconds;
			return this;
		}

		public Builder readTimeout(int readTimeoutInSeconds) {
			this.readTimeout = readTimeoutInSeconds;
			return this;
		}

		public Builder keepAlive(int keepAliveInSeconds) {
			this.keepAlive = keepAliveInSeconds;
			return this;
		}

		public RestClient build() {
			OkHttpClient client = new OkHttpClient.Builder()
					// set connect timeout:
					.connectTimeout(this.connectTimeout, TimeUnit.SECONDS)
					// set read timeout:
					.readTimeout(this.readTimeout, TimeUnit.SECONDS)
					// set connection pool:
					.connectionPool(new ConnectionPool(0, this.keepAlive, TimeUnit.SECONDS))
					// do not retry:
					.retryOnConnectionFailure(false).build();
			String endpoint = this.scheme + "://" + this.host;
			if (this.port != (-1)) {
				endpoint = endpoint + ":" + this.port;
			}
			return new RestClient(endpoint, this.host, this.apiKey, this.apiSecret, client);
		}
	}

	RestClient(String endpoint, String host, String apiKey, String apiSecret, OkHttpClient client) {
		this.endpoint = endpoint;
		this.host = host;
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.client = client;
	}

	public <T> T get(Class<T> clazz, String path, Map<String, String> query) {
		Objects.requireNonNull(clazz);
			return request(clazz, null, "GET", path, query, null);
	}

	public <T> T get(TypeReference<T> ref, String path, Map<String, String> query) {
		Objects.requireNonNull(ref);
			return request(null, ref, "GET", path, query, null);
	}

	public <T> T post(Class<T> clazz, String path, Object body) {
		Objects.requireNonNull(clazz);
			return request(clazz, null, "POST", path, null, body);
	}

	public <T> T post(TypeReference<T> ref, String path, Object body) {
		Objects.requireNonNull(ref);
		
			return request(null, ref, "POST", path, null, body);
	}

	<T> T request(Class<T> clazz, TypeReference<T> ref, String method, String path, Map<String, String> query,
			Object body) {
		if (!path.startsWith("/")) {
			throw new IllegalArgumentException("Invalid path: " + path);
		}

		// build payload:
		StringBuilder payloadToSign = new StringBuilder(1024)
				// method:
				.append(method).append('\n')
				// host:
				.append(host).append('\n')
				// path:
				.append(path).append('\n');

		// query:
		String queryString = null;
		if (query != null) {
			List<String> paramList = new ArrayList<>();
			for (Map.Entry<String, String> entry : query.entrySet()) {
				paramList.add(entry.getKey() + "=" + entry.getValue());
			}
			Collections.sort(paramList);
			queryString = String.join("&", paramList);
			payloadToSign.append(queryString).append('\n');
		} else {
			payloadToSign.append('\n');
		}
		StringBuilder urlBuilder = new StringBuilder(64).append(this.endpoint).append(path);
		if (queryString != null) {
			urlBuilder.append('?').append(queryString);
		}
		final String url = urlBuilder.toString();

		// json body:
		final String jsonBody = body == null ? "" : JsonUtil.writeJson(body);

		Request.Builder requestBuilder = new Request.Builder().url(url);
		if ("POST".equals(method)) {
			requestBuilder.post(RequestBody.create(JSON, jsonBody));
		}

		final String timestamp = String.valueOf(System.currentTimeMillis());
		final String uniqueId = UUID.randomUUID().toString().replace("-", "");

		// header:
		List<String> headerList = new ArrayList<>();
		headerList.add(HEADER_API_KEY + ": " + this.apiKey);
		headerList.add(HEADER_API_SIGNATURE_METHOD + ": " + SIGNATURE_METHOD);
		headerList.add(HEADER_API_SIGNATURE_VERSION + ": " + SIGNATURE_VERSION);
		headerList.add(HEADER_API_TIMESTAMP + ": " + timestamp);
		headerList.add(HEADER_API_UNIQUE_ID + ": " + uniqueId);

		Collections.sort(headerList);
		for (String header : headerList) {
			payloadToSign.append(header).append('\n');
		}

		requestBuilder.addHeader(HEADER_API_KEY, this.apiKey);
		requestBuilder.addHeader(HEADER_API_SIGNATURE_METHOD, SIGNATURE_METHOD);
		requestBuilder.addHeader(HEADER_API_SIGNATURE_VERSION, SIGNATURE_VERSION);
		requestBuilder.addHeader(HEADER_API_TIMESTAMP, timestamp);
		requestBuilder.addHeader(HEADER_API_UNIQUE_ID, uniqueId);

		// append body:
		payloadToSign.append(jsonBody);
		// sign:
		String sign = HashUtil.hmacSha256(payloadToSign.toString().getBytes(StandardCharsets.UTF_8), apiSecret);
		requestBuilder.addHeader(HEADER_API_SIGNATURE, sign);

		this.lastRequest = requestBuilder.build();
		return retry(clazz, ref);
	}

	public <T> T retry(Class<T> clazz, TypeReference<T> ref) {
		if (this.lastRequest == null) {
			throw new RuntimeException("Cannot execute previous request.");
		}
		try {
			return execute(clazz, ref, this.lastRequest);
		} catch (IOException e) {
			throw new RuntimeException("IOException", e);
		}
	}

	<T> T execute(Class<T> clazz, TypeReference<T> ref, Request request) throws IOException {
		logger.info("request: {}...", request.url().url());
		Response response = this.client.newCall(request).execute();
		if (response.code() == 200) {
			try (ResponseBody body = response.body()) {
				logger.info("response 200.");
				String json = body.string();
				if ("null".equals(json)) {
					return null;
				}
				return clazz == null ? JsonUtil.readJson(json, ref) : JsonUtil.readJson(json, clazz);
			}
		} else if (response.code() == 400) {
			try (ResponseBody body = response.body()) {
				logger.info("response 400.");
				ApiErrorResponse err = JsonUtil.readJson(body.string(), ApiErrorResponse.class);
				throw new ApiException(err.error, err.data, err.message);
			}
		} else if (response.code() == 429) {
			// should not happen:
			throw new RuntimeException("Rate limit");
		} else {
			throw new RuntimeException("Http error: " + response.code());
		}
	}

	static final String HEADER_API_KEY = "API-KEY";
	static final String HEADER_API_SIGNATURE = "API-SIGNATURE";
	static final String HEADER_API_SIGNATURE_METHOD = "API-SIGNATURE-METHOD";
	static final String HEADER_API_SIGNATURE_VERSION = "API-SIGNATURE-VERSION";
	static final String HEADER_API_TIMESTAMP = "API-TIMESTAMP";
	static final String HEADER_API_UNIQUE_ID = "API-UNIQUE-ID";

	static final String SIGNATURE_METHOD = "HmacSHA256";
	static final String SIGNATURE_VERSION = "1";

	static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

}
