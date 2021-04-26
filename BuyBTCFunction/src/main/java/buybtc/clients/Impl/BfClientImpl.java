package buybtc.clients.Impl;

import java.util.HashMap;
import java.util.Map;

import java.time.Instant; //for timestamp
import javax.crypto.Mac; //for sign
import javax.crypto.spec.SecretKeySpec; // for sign

import com.google.gson.Gson;

import buybtc.beans.Impl.BfOrderParamsImpl;
import buybtc.beans.Impl.BfOrderResImpl;
import buybtc.beans.Impl.BfTickerImpl;
import buybtc.beans.bitflyer.ParentOrderParams;
import buybtc.beans.bitflyer.ParentOrderRes;
import buybtc.clients.APIClient;
import buybtc.enums.ProductCode;
import buybtc.utils.HttpRequestParameters;
import buybtc.utils.HttpUtils;

public class BfClientImpl implements APIClient<BfTickerImpl, BfOrderResImpl, BfOrderParamsImpl, ParentOrderRes, ParentOrderParams> {
	private String apiKey;
	private String apiSecret;
	
	private final String API_URL = "https://api.bitflyer.com";
	
	public BfClientImpl(String apiKey, String apiSecret) {
		this.setApiKey(apiKey);
		this.setApiSecret(apiSecret);
	}
	
	@Override
	public BfTickerImpl getTicker(ProductCode code) {
		String path = "/v1/ticker";
		String method = "GET";
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("product_code", code.getCode());
		
		var params = new HttpRequestParameters(
				method,
				API_URL + path, //https://api.bitflyer.com/v1/ticker
				null,
				queryParams,
				null
				);
		
		String res = new HttpUtils().DoHttpRequest(params);
		
		Gson gson = new Gson();
		BfTickerImpl ticker = gson.fromJson(res, BfTickerImpl.class);
		return ticker;
	}
	
	@Override
	public BfOrderResImpl placeOrder(BfOrderParamsImpl params) {
		String path = "/v1/me/sendchildorder";
		String method = "POST";
		Gson gson = new Gson();
		String body = gson.toJson(params);
		
		Map<String, String> header = getHeader(method, path, body);
		
		var httpParams = new HttpRequestParameters(
				method,
				API_URL + path,
				header,
				null,
				body
				);
		
		
		var res = new HttpUtils().DoHttpRequest(httpParams);
		System.out.println(res);
		
		var orderRes = gson.fromJson(res, BfOrderResImpl.class);
		return orderRes;
	}
	
	@Override
	public ParentOrderRes placeSpecialOrder(ParentOrderParams params) {
		String path = "/v1/me/sendparentorder";
		String method = "POST";
		Gson gson = new Gson();
		String body = gson.toJson(params);
		
		Map<String, String> header = getHeader(method, path, body);
        
		var httpParams = new HttpRequestParameters(
				method,
				API_URL + path,
				header,
				null,
				body
				);
		
        String res = new HttpUtils().DoHttpRequest(httpParams);
        
		System.out.println("------");
		System.out.println(String.format("{ \"res\": \"%s\"}", res));
		System.out.println("------");
 
        ParentOrderRes orderRes = new ParentOrderRes();
        orderRes = gson.fromJson(res, ParentOrderRes.class);
        return orderRes;
	}
	
	private Map<String, String> getHeader(String method, String path, String body) {
		long now = Instant.now().toEpochMilli();
		String timestamp = String.valueOf(now / 1000L);
		
		String text = timestamp + method + path + body;
		String sign = getSign(text);
		
		Map<String, String> header = new HashMap<String, String>();
		header.put("ACCESS-KEY", this.apiKey);
		header.put("ACCESS-TIMESTAMP", timestamp);
		header.put("ACCESS-SIGN", sign);
		header.put("Content-Type", "application/json");
		return header;
	}
	
	private String getSign(String text) {
		final String ALGO = "HmacSHA256";
		try {
			Mac mac = Mac.getInstance(ALGO);
			mac.init(new SecretKeySpec(this.apiSecret.getBytes(), ALGO));
			byte[] hash = mac.doFinal(text.getBytes());
			
			StringBuilder sb = new StringBuilder();
			for(byte b : hash) {
				//javaのbyteは -128~127の整数
				//→　unsignedにするために0xff(255)と論理積を計算
				sb.append(String.format("%02x", b & 0xff));
				//%02x　→　16進数に変換
			}
			return sb.toString();
		} catch(Exception e) {
			System.out.println("Failed to get Signature....");
			return null;
		}
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}
	
}