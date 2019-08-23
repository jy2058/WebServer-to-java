package kr.letech.webServer.db;

import kr.letech.webServer.util.CustomConcurrentHashMap;
import kr.letech.webServer.vo.UserVO;

public class Repository {
	private static CustomConcurrentHashMap<String, UserVO> USER_DB;
	static {
		Repository.USER_DB = new CustomConcurrentHashMap<>();
		UserVO userVO = new UserVO();
		userVO.setId("admin");
		userVO.setPw("123");
		userVO.setName("관리자");

		Repository.USER_DB.put(userVO.getId(), userVO);
		userVO = new UserVO();
		userVO.setId("user1");
		userVO.setPw("123");
		userVO.setName("유저");
		Repository.USER_DB.put(userVO.getId(), userVO);

	}

	public static UserVO getUser(String userId) {

		UserVO userVO = Repository.USER_DB.get(userId);
		/*CustomConcurrentHashMap<String, UserVO> userInfo = new CustomConcurrentHashMap<String, UserVO>();
		userInfo.put(userId, userVO);*/
		return userVO;
//		return Repository.USER_DB.get();
	}

	public static void main(String[] args) {
		System.out.println("aa");
//		System.out.println(Repository.getUser());
		System.out.println(Repository.getUser("admin"));
	}
}

