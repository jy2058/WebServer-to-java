package kr.letech.webServer.vo;

import kr.letech.webServer.base.BaseVO;

public class UserVO extends BaseVO {
	private static final long serialVersionUID = -4112565278704001558L;

	private String id;
	private String pw;
	private String name;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
