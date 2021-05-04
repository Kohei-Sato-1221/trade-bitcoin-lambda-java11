package buybtc.utils;

import java.io.IOException;
import java.net.URI;
//Java11から追加された標準のHttpClient
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class HttpUtils {
	
	//HttpRequestを送るUtil関数
	public String DoHttpRequest(HttpRequestParameters params) {
		HttpClient client = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_2)
				.build();
		
		Map<String, String> header = params.getHeader();
		String body = params.getBody();
		
		HttpRequest request;
		if (params.getMethod().equals("GET")){
			request = HttpRequest.newBuilder()
					.uri(URI.create(params.getURL() + params.getPathFromParams()))
					.GET()
					.build();
		} else {
			var builder = HttpRequest.newBuilder();
			header.forEach((k, v) -> {
				builder.setHeader(k, v);
			});
			
			var bodyParam = HttpRequest.BodyPublishers.ofString(body);
			
			request = builder
					.uri(URI.create(params.getURL() + params.getPathFromParams()))
					.POST(bodyParam)
					.build();
		}
				
		try {
			HttpResponse<String> response = client.send(
					request, HttpResponse.BodyHandlers.ofString());
			return response.body();
		} catch(IOException e) {
			e.printStackTrace();
			return "error";
		} catch(InterruptedException e) {
			e.printStackTrace();
			return "error";			
		}
	}
}





