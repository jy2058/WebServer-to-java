package kr.letech.webServer.exec;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import kr.letech.webServer.cons.Constants;
import kr.letech.webServer.util.CloseUtils;

public class WebServer {
	public static void main(String[] args) {
		Executor executor = Executors.newFixedThreadPool(3000);
		// Runtime.getRuntime().addShutdownHook : 종료 명령을 받으면, 해당 Thread 실행
		ServerSocket ss = null;
		Server thread = null;
		Socket client = null;
		SessionCheckerThread sThread = null;

		try {
			ss = new ServerSocket(Constants.WEB_SERVER_PORT);
			System.out.println("Server start ==> port : " + Constants.WEB_SERVER_PORT);

			// 세션감시 스레드
			sThread = new SessionCheckerThread();
			sThread.start();

			while ((client = ss.accept()) != null) {
				System.out.println("Client start ==> port : " + client.getPort());

				thread = new Server(client);
				executor.execute(thread);
//				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseUtils.quiteClose(ss);
			Constants.IS_RUN = false;	// 세션감시 스레드 종료.
		}
	}
}