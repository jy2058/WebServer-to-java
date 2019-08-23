package kr.letech.webServer.cons;

import java.io.File;

public class Constants {
	public static int WEB_SERVER_PORT = 18080;
	public static int SESSION_END_TIME = 30 * 60 * 1000;	// 30분
//	public static int SESSION_END_TIME = 30;	// 30분
	public static boolean IS_RUN = true;

	public static String CLASSPATH = (new File("")).getAbsolutePath() + "/src/kr/letech/webServer/html";
}
