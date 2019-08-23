package kr.letech.webServer.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import kr.letech.webServer.cons.Constants;
import kr.letech.webServer.servlet.Servlet;
import kr.letech.webServer.vo.UserVO;

public class HttpResponse {
	private String cookie = null;
	private boolean sFlag = false;	// true : 생성 필요
	File p = new File("");
//	String classpath = p.getAbsolutePath();
//	URL url = ClassLoader.getSystemResource("");
//	private String CLASSPATH = url.getPath() + "kr/letech/webServer/view";

	private Map<String, String> respHeader = new HashMap<>();
	private BufferedWriter writer;

	public HttpResponse(OutputStream output) throws IOException {
		writer = new BufferedWriter(new OutputStreamWriter(output));
	}

	public void forward(String path, String contentType, String cookie) throws IOException {
		forward(path, contentType, null, cookie);
//		this.respBody(body);

	}

	public void forward(String path, String contentType, Map<String, String> replaceMap, String cookie) throws IOException {
		// replaceMap에 "\\$\\{userName\\}", "관리자" 처럼 처리할 것들 넣어서 관리
		System.err.println("path : " + Constants.CLASSPATH + path);

//		UserVO userInfo = (UserVO)Servlet.httpSession.getAttribute("userInfo");
//		replaceMap.put("userName", userInfo.getName());

		File body = new File(Constants.CLASSPATH + path);
		if (!body.exists()) {

		}
		System.out.println("path: "+path);
		System.out.println("body: "+body);
		if (StringUtils.isEmpty(contentType)) {
			contentType = "text/html";
		}

		if ("/404.html".equals(path)) {
			contentType = "text/html";
		}

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(body)));
			String html = "";
			String tmp = null;
			String username = "아무개";
			if (cookie != null && "text/html".equals(contentType) && !"/404.html".equals(path)) {
				HttpSession session = Servlet.getSession(cookie);
				Object userObj = session.getAttribute("userInfo");
				if (userObj == null) {
					sendRedirect("http://localhost:18080/login.do");
					return;
				} else {
					UserVO userVO = (UserVO) userObj;
					username = userVO.getName();
				}
			}
			while(br.ready()) {
				tmp = br.readLine() + "\r\n";
				tmp = tmp.replaceAll("\\$\\{userName\\}", username);
//				tmp = tmp.replaceAll("\\$\\{userName\\}", replaceMap.get("userName"));

				html += tmp;
			}

			this.setHeader("Content-Length", String.valueOf(html.getBytes().length));
			this.setHeader("Content-Type", contentType);
			if(sFlag) {
//			this.makeSession();
				this.setHeader("Set-Cookie", "JYSESSION="+this.cookie);
			}

			if("/404.html".equals(path)) {
				this.resp404();
			}else {
				this.resp200();
			}
			writer.write(html);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			CloseUtils.quiteClose(br, writer);
		}

	}

	public void sendRedirect(String path) throws IOException {
		System.out.println("redirect path : " + path);
		this.setHeader("Location", path);
		this.resp302();
		writer.flush();
		writer.close();
	}
	private void setHeader(String key, String value) {
		respHeader.put(StringUtils.trim(key), StringUtils.trim(value));
	}
	private void writeHeader() throws IOException {
		for(String key : respHeader.keySet()) {
			writer.write(key + ": " + respHeader.get(key) + "\r\n");
		}
	}
	private void resp200() {
		try {
			writer.write("HTTP/1.1 200 OK \r\n");
			this.writeHeader();
			writer.write("\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void resp302() {
		try {
			writer.write("HTTP/1.1 302 Found \r\n");
			this.writeHeader();
//			writer.write("\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void resp404() {
		try {
			writer.write("HTTP/1.1 404 Not Found \r\n");
			this.writeHeader();
			writer.write("\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String makeCookie() {
		String uu = UUID.randomUUID().toString();
		this.cookie = uu;
		this.sFlag = true;
		return cookie;
	}

}
