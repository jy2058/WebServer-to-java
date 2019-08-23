package kr.letech.webServer.servlet;

import kr.letech.webServer.util.CustomConcurrentHashMap;
import kr.letech.webServer.util.HttpRequest;
import kr.letech.webServer.util.HttpResponse;
import kr.letech.webServer.util.HttpSession;
import kr.letech.webServer.vo.UserVO;

public abstract class Servlet {
	private static CustomConcurrentHashMap<String, HttpSession> SESSION = new CustomConcurrentHashMap<>();

	public static HttpSession getSession(String cookie) {
		HttpSession httpSession = Servlet.SESSION.get(cookie);
		if (httpSession == null) {
			return new HttpSession<>();
		}
		return Servlet.SESSION.get(cookie);
	}

	public static CustomConcurrentHashMap<String, HttpSession> getAllSession() {
		return Servlet.SESSION;
	}

	public static void setSession(String key, HttpSession value) {
		SESSION.put(key, value);
	}

	public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
		String method = httpRequest.getReqMethod();
		System.out.println("method : " + method);
		if("GET".equals(method)){
			doGet(httpRequest, httpResponse);
		}else if("POST".equals(method)){
			doPost(httpRequest, httpResponse);
		}
	}

	protected abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse);

	protected  abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse);
}
