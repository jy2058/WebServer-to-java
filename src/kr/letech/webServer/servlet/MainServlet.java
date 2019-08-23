package kr.letech.webServer.servlet;

import java.io.IOException;

import kr.letech.webServer.db.Repository;
import kr.letech.webServer.util.HttpRequest;
import kr.letech.webServer.util.HttpResponse;
import kr.letech.webServer.util.HttpSession;
import kr.letech.webServer.vo.UserVO;

public class MainServlet extends Servlet {

	@Override
	protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
		System.out.println("doGet");
		String contentType = "";
		String cookie = httpRequest.getCookie();
		try {
			httpResponse.forward("/ss.html", contentType, cookie);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		System.out.println("doPost");
		String userId = httpRequest.getQuery("userId");
		String userPw = httpRequest.getQuery("userPw");

		if (userId != null && userPw != null) {
			UserVO userInfo = Repository.getUser(userId);
			if (userInfo != null) {
				if (userId.equals(userInfo.getId()) && userPw.equals(userInfo.getPw())) {
					String cookie = httpRequest.getCookie();
					System.out.println("쿠키 : " + cookie);
					if ("".equals(cookie)) {
						cookie = httpResponse.makeCookie();
						System.out.println("쿠키 생성 ");
					}
					System.out.println(userId + " 로그인 성공");

//					Map<String,String> replaceMap = new HashMap<String, String>();
//					replaceMap.put("userName", userInfo.getName());

					HttpSession httpSession = new HttpSession();
					httpSession.setAttribute("userInfo", userInfo);
					Servlet.setSession(cookie, httpSession);

					try {
//						httpResponse.forward("/ss.html", httpRequest.getMimeType(), replaceMap);
						httpResponse.forward("/ss.html", httpRequest.getMimeType(), cookie);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println(userId + " 로그인 실패");
					try {
//						httpResponse.forward("/ss.html", httpRequest.getMimeType());
						httpResponse.sendRedirect("http://localhost:18080/login.do");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				System.out.println("userInfo null");
				try {
//					httpResponse.forward("/ss.html", httpRequest.getMimeType());
					httpResponse.sendRedirect("http://localhost:18080/login.do");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				System.out.println("아이디 없음");
//				httpResponse.forward("/ss.html", httpRequest.getMimeType());
				httpResponse.sendRedirect("http://localhost:18080/login.do");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
