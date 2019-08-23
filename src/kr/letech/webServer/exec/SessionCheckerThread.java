package kr.letech.webServer.exec;

import java.util.Date;

import kr.letech.webServer.cons.Constants;
import kr.letech.webServer.servlet.Servlet;
import kr.letech.webServer.util.HttpSession;

public class SessionCheckerThread extends Thread {

	@Override
	public void run() {
		System.err.println("session쓰레드 생성");
		// Servlet.getSession(cookie);
		long sleepTime = 0;
		while (Constants.IS_RUN) {
			// 1. 모든 세션 가져와서
			// 2. 최종 접속시간 확인
			// 3. 30분이 지나면 해당 세션 정보 삭제
			// 4. 1초 동안 sleep
			/**
			 * HttpSession session = new HttpSession();
			 * Servlet.setSession(cookie, session);
			 * Servlet.getSession(cookie); ==> session session.setAttribute("USER", userVO);
			 */
			for(String key : Servlet.getAllSession().keySet()) {
				HttpSession httpSession = Servlet.getAllSession().get(key);
				if(httpSession.isTimeoverSession()) {
					Servlet.getAllSession().remove(key);
				}
			}
//			System.err.println(Servlet.getAllSession().keySet());

			// 123456789;
			//       +211 => 이 시간을 계산해야 함
			// 123457000;
			// 123458000;
			try {
				sleepTime = ((System.currentTimeMillis() / 1000L) * 1000L + 1000) - System.currentTimeMillis();
				if (sleepTime > 0) {
					sleep(sleepTime);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		HttpSession session = new HttpSession();
		HttpSession session2 = new HttpSession();
		session.setAttribute("a", "a");
		session.setAttribute("b", "b");
		session.setAttribute("c", "c");
		session2.setAttribute("D", "D");
		session2.setAttribute("E", "E");
		session2.setAttribute("F", "F");

//		session.setLastConnTime(session.getLastConnTime() - 10000000);
//		session.setLastConnTime(session.getLastConnTime() + Constants.SESSION_END_TIME);
		session.setLastConnTime(System.currentTimeMillis() + 10);

		System.out.println("session1 " + session.isTimeoverSession());
		System.out.println("session2 " + session2.isTimeoverSession());

		Servlet.setSession("aaa", session);
		Servlet.setSession("bbb", session2);

		System.out.println("삭제 전 : " + Servlet.getAllSession().keySet());
		for(String key : Servlet.getAllSession().keySet()) {
			// key 쿠키  / value 세션.
			HttpSession httpSession = Servlet.getAllSession().get(key);
			System.out.println("키 : " + key + " value : " + httpSession);
			System.out.println("value 마지막 시간 : " + httpSession.getLastConnTime());


			if(httpSession.getLastConnTime() != System.currentTimeMillis()) {
				return;
			}else {

			}

			// 생성되자 마자 체크하면 무조건 현재시간보다 큼.
			if(httpSession.isTimeoverSession()) {
				System.out.println("httpSession.getLastConnTime() : " + httpSession.getLastConnTime());
				System.out.println("System.currentTimeMillis() : " + System.currentTimeMillis());
				Servlet.getAllSession().remove(key);
			}
		}
		System.out.println("삭제 후 : " + Servlet.getAllSession().keySet());


		/*Enumeration<String> allSession = Servlet.getAllSession().keys();

		while(allSession.hasMoreElements()) {
			String key = allSession.nextElement();
			System.out.println(Servlet.getAllSession().get(key));
		}*/


//		}
	}
}
