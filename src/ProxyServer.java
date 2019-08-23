

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyServer {
	public static int PROXY_SERVER_PORT = 18081;
//	public static String PROXY_TARGET_URL = "127.0.0.1";
//	public static int PROXY_TARGET_PORT = 8080;
	public static String PROXY_TARGET_URL = "www.letech.kr";
	public static int PROXY_TARGET_PORT = 80;
	public static int PROXY_TARGET_TIMEOUT = 10; // 타셋 서버 응답 대기 시간, 10초

	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(ProxyServer.PROXY_SERVER_PORT);
		System.out.println("start proxy server!!! port : " + ProxyServer.PROXY_SERVER_PORT);
		while (true) {
			Socket socket = ss.accept();
			ProxyThread proxyThread = new ProxyThread(socket);
			proxyThread.start();
		}
	}
}

class ProxyThread extends Thread {
	private Socket socket;
	public ProxyThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream clientIs = null;
		OutputStream clientOs = null;
		Socket httpSocket = null;
		InputStream httpIs = null;
		OutputStream httpOs = null;
		try {
			clientIs = this.socket.getInputStream();
			clientOs = this.socket.getOutputStream();
			httpSocket = new Socket(ProxyServer.PROXY_TARGET_URL, ProxyServer.PROXY_TARGET_PORT);
			httpIs = httpSocket.getInputStream();
			httpOs = httpSocket.getOutputStream();

			transferStream(clientIs, httpOs);
			// 서버에서 응답을 받을 받기 위해 기다린다.
			int timeoutCnt = (ProxyServer.PROXY_TARGET_TIMEOUT * 1000) / 10;
			int cnt = 0;
			while (httpIs.available() <= 0 && cnt++ < timeoutCnt) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
			transferStream(httpIs, clientOs);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			quiteClose(clientIs, clientOs, httpIs, httpOs);
		}
	}

	private void transferStream(InputStream is, OutputStream os) throws IOException {
		int data = 0;
//		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		while (is.available() > 0) {
			data = is.read();
			os.write(data);
//			bais.write(data);
		}
//		System.err.println(new String(bais.toByteArray()));
		os.flush();
	}

	private void quiteClose(Closeable... closeables) {
		for (Closeable closeable: closeables) {
			try {
				if (closeable != null) closeable.close();
			} catch (IOException e) {
			}
		}
	}
}