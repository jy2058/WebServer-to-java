package kr.letech.webServer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tika.Tika;

import kr.letech.webServer.exec.Mapping;
import kr.letech.webServer.servlet.Servlet;

public class HttpRequest {
	private String reqPath;
	private String reqMethod;
	private String url;
	private String mimeType;
	private boolean resourceFlag;
	private Map<String, String> query = new HashMap<String, String>();

	private BufferedReader reader;
	private Map<String, String> reqHeader = new HashMap<>();

	public HttpRequest(InputStream input) throws IOException {
		reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
		try {
			String readLine = reader.readLine();
			if (readLine == null) {
	            return;
	        }
			this.setReqPath(readLine);
			this.setUrl();
//			this.setReqMethod(readLine);
			this.setReqHeader(readLine);
			this.setQuery();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getReqPath() {
		return reqPath;
	}

	public void setReqPath(String readLine) {
		String[] split = readLine.split(" ");
		this.reqMethod = split[0];
		this.reqPath = split[1];
	}

	public String getReqMethod() {
		return reqMethod;
	}

	/*public void setReqMethod(String readLine) {
		String[] split = readLine.split(" ");
		this.reqMethod = split[0];
	}*/

	public String getUrl() {
		return url;
	}

	public void setUrl() {
		this.url = this.reqPath.contains("?") ? this.reqPath.split("\\?")[0] : this.reqPath;

		// 무작위로 넣은 주소....는 resourceFlag가 false여야 돼. 어떻게 구분하지...
		if(this.url.endsWith(".do") || this.url.equals("/")) {
			this.mimeType = "text/html";
			this.resourceFlag = false;
			/*Servlet servlet = Mapping.getMapping(this.url);
			if (servlet == null) {
			}*/
		}else {
			this.mimeType = new Tika().detect(this.url);
			/*if(this.url.endsWith(".ico")) {
				this.mimeType = "image/*";
			}*/
			this.resourceFlag = true;
		}
	}

	public String getQuery(String key) {
		return query.get(key);
	}

	public void setQuery() throws IOException {
		if("GET".equals(this.reqMethod)) {
			if(!this.getReqPath().contains("?")) {
				return;
			}else {
				String[] split = this.getReqPath().split("\\?");
				String queryData = split[1];
				String[] querySplit = queryData.split("&");
				if(querySplit.length > 0) {
					for(int i = 0; i < querySplit.length; i++) {
						String[] queryString = querySplit[i].split("=");
						if(queryString.length == 2 && queryString[1] != null) {
							this.query.put(queryString[0], queryString[1]);
						}
					}
				}
			}

		}else if("POST".equals(this.reqMethod)) {
//			int contentLength = NumberUtils.toInt(this.reqHeader.get("Content-Length"));
			String queryData = RequestDataUtil.getData(this.reader);
			if(StringUtils.isNotEmpty(queryData)) {
				String[] querySplit = queryData.split("&");
				if(querySplit.length > 0) {
					for(int i = 0; i < querySplit.length; i++) {
						String[] queryString = querySplit[i].split("=");
						if(queryString.length == 2 && queryString[1] != null) {
							this.query.put(queryString[0], queryString[1]);
						}
					}
				}
			}


		}
	}

	public String getReqHeader(String key) {
		return reqHeader.get(key);
	}

	public void setReqHeader(String header) throws IOException {
		while(!"".equals(header)) {
			header = reader.readLine();
			System.out.println("header = " + header );
			if(!"".equals(header)){
				String[] split = header.split(":");
				reqHeader.put(StringUtils.trim(split[0]), StringUtils.trim(split[1]));
			}else {
				header = "";
			}
		}
		reqHeader.put("Accept", mimeType);
	}

	public boolean isResourceFlag() {
		return resourceFlag;
	}

	public String getMimeType() {
		return mimeType;
	}

	public String getCookie() {
		String cookie = "";
		String reqCookie = reqHeader.get("Cookie");
		System.out.println("쿠키 : " + reqCookie);
		if(!"".equals(reqCookie) && reqCookie != null) {
			String[] split = reqCookie.split(";");
			for(int i = 0; i < split.length; i++) {
				System.out.println(split[i]);
				if(StringUtils.trim(split[i]).startsWith("JYSESSION")) {
					cookie = split[i];
					System.out.println("쿠키 존재 : " + cookie);
				};
			}
		}
		return cookie;
	}

}
