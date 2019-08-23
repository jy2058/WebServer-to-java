package kr.letech.webServer.vo;

import kr.letech.webServer.base.BaseVO;

public class SessionVO extends BaseVO{
	private static final long serialVersionUID = 8421601609645318394L;

	private long sTime;

	public SessionVO() {
		this.sTime = System.currentTimeMillis();
	}

	public long getsTime() {
		return sTime;
	}

	public void setsTime(long sTime) {
		this.sTime = sTime;
	}
}

