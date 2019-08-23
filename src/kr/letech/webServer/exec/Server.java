package kr.letech.webServer.exec;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import kr.letech.webServer.cons.Constants;
import kr.letech.webServer.servlet.Servlet;
import kr.letech.webServer.util.HttpRequest;
import kr.letech.webServer.util.HttpResponse;

public class Server extends Thread {
	private Socket client;

	public Server(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		try {
			InputStream input = client.getInputStream();
			OutputStream output = client.getOutputStream();

			HttpRequest httpRequest = new HttpRequest(input);
			HttpResponse httpResponse = new HttpResponse(output);
			System.err.println("httpRequest.getUrl() : " + httpRequest.getUrl());


			if(httpRequest.isResourceFlag()) {
				String path = "/.." + httpRequest.getUrl();
				System.out.println("Constants.CLASSPATH : " + Constants.CLASSPATH);
				File resource = new File(Constants.CLASSPATH + path);
				System.out.println("!resource.exists() : " + !resource.exists());
				if (!resource.exists() || !resource.isFile()) {
					path = "/404.html";
					httpResponse.forward(path, httpRequest.getReqHeader("Accept"), httpRequest.getCookie());
				} else {
					httpResponse.forward(path, httpRequest.getReqHeader("Accept"), httpRequest.getCookie());
				}
			}else {
				Servlet servlet = Mapping.getMapping(httpRequest.getUrl());
				if(servlet == null) {
					System.out.println("servlet null");
					String path = "/404.html";
					httpResponse.forward(path, httpRequest.getReqHeader("Accept"), httpRequest.getCookie());

				}else {
						/*			// 세션 없으면 로그인페이지로.
				if(Servlet.getSession(httpRequest.getCookie()) == null) {
					httpResponse.sendRedirect("http://localhost:18080/login.do");
				}*/
					servlet.service(httpRequest, httpResponse);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if(client != null) {
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
