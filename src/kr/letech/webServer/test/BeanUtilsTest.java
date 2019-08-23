package kr.letech.webServer.test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import kr.letech.webServer.vo.UserVO;

public class BeanUtilsTest {
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
		Map<String, String> map = new HashMap<>();
		map.put("id", "abc");
		map.put("pw", "123");
		map.put("name", "이름");
		UserVO vo = new UserVO();
		// jsp에서 넘어온 값을 간단하게 java bean 객체에 맞추어 값을 넣어준다.
		BeanUtils.populate(vo, map);
		System.out.println(vo);
	}
}
