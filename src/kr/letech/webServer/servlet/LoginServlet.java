package kr.letech.webServer.servlet;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import kr.letech.webServer.util.HttpRequest;
import kr.letech.webServer.util.HttpResponse;
import kr.letech.webServer.util.HttpSession;

public class LoginServlet extends Servlet{

	@Override
	protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

		System.out.println("doGet");
		String path = "/login.html";

		String cookie = httpRequest.getCookie();
		HttpSession session = Servlet.getSession(cookie);
		Object userObj = session.getAttribute("userInfo");
		if (userObj != null) {
			path = "http://localhost:18080/index.do";
			try {
				httpResponse.sendRedirect(path);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			try {
				httpResponse.forward(path, httpRequest.getReqHeader("Accept"), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		System.out.println("doPost");

	}


}
