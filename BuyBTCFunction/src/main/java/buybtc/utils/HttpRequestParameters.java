package buybtc.utils;

import java.util.Map;

public class HttpRequestParameters {
	private String method;  // get or post
	private String URL;
	private Map<String, String> header;
	private Map<String, String> queryParams;
	private String body;
	

	
	//コンストラクタ
	public HttpRequestParameters(
			String method, 
			String URL,
			Map<String, String> header,
			Map<String, String> queryParams,
			String body) {
		this.setMethod(method);
		this.setURL(URL);
		this.setHeader(header);
		this.setQueryParams(queryParams);
		this.setBody(body);
	}
	
	
	public String getPathFromParams() {
		if (queryParams == null) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		boolean isFirst = true;
		
		for(Map.Entry<String, String> entry : queryParams.entrySet()) {
			if (isFirst) {
				sb.append("?");
				isFirst = false;
			} else {
				sb.append("&");
			}
			
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
		}
		
		return sb.toString();
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, String> queryParams) {
		this.queryParams = queryParams;
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
