package kr.letech.webServer.util;

import kr.letech.webServer.cons.Constants;

public class HttpSession<String, Object> extends CustomConcurrentHashMap<String, Object> {
	private long lastConnTime;

	public HttpSession() {
		this.lastConnTime = System.currentTimeMillis();
	}

	public Object getAttribute(String key) {
		return this.get(key);
	}
	public void setAttribute(String key, Object value) {
		this.put(key, value);
	}
	public long getLastConnTime() {
		return lastConnTime;
	}
	public void setLastConnTime(long lastConnTime) {
		this.lastConnTime = lastConnTime;
	}
	public boolean isTimeoverSession() {
		return this.lastConnTime + Constants.SESSION_END_TIME < System.currentTimeMillis();
	}
}

