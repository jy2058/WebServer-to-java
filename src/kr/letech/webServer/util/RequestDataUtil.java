package kr.letech.webServer.util;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Arrays;

public class RequestDataUtil {
	public static String getData(BufferedReader br) throws IOException {

//		char[] body = new char[contentLength];
//		br.read(body, 0, contentLength);
//		return String.valueOf(body);

		// contentLength 없이 사용
		int bufferSize = 1024;
		CharArrayWriter caw = new CharArrayWriter();
		// 새로운 문자 버퍼 할당
		CharBuffer buffer = CharBuffer.allocate(bufferSize);
		int readCnt = 0;
		while (true) {
			readCnt = br.read(buffer);
			// 읽은 개수
			if (readCnt == -1 || readCnt == 0) {
				break;
			} else if (readCnt < bufferSize) {
				// 버퍼 남은 공간에 0으로 값이 들어가 있는데 그 부분을 자르고 딥카피
				caw.write(Arrays.copyOf(buffer.array(), readCnt));
				break;
			} else {
				caw.write(buffer.array());
			}
			// 버퍼 position을 0으로 초기화
			buffer.flip();
		};
		caw.flush();

		String result = String.valueOf(caw.toCharArray());

		return result;

	}



}
