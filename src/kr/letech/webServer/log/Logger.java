package kr.letech.webServer.log;

import java.util.Date;

public class Logger {
	private static Logger instance = new Logger();
	private Logger() {
	}
	
	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
		}
		return instance;
	}
	
	public void log(String msg) {
		System.out.println(new Date().toLocaleString() + " : " + msg);
	}
	public void err(String msg) {
		System.err.println(new Date().toLocaleString() + " : " + msg);
	}
}
