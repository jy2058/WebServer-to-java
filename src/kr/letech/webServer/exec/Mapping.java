package kr.letech.webServer.exec;

import java.util.HashMap;
import java.util.Map;

import kr.letech.webServer.servlet.LoginServlet;
import kr.letech.webServer.servlet.Servlet;
import kr.letech.webServer.servlet.MainServlet;

public class Mapping {
	private static Map<String, Servlet> mapping = new HashMap<String, Servlet>();

	static{
		mapping.put("/login.do", new LoginServlet());
		mapping.put("/index.do", new MainServlet());
		mapping.put("/", new LoginServlet());
	}

	public static Servlet getMapping(String path) {
		return mapping.get(path);
	}
}
