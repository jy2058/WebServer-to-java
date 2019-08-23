package kr.letech.webServer.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe한 HashMap.
 * ConcurrentHashMap를 상속 받아서 구현하였으며,
 * put 값이 null 인 경우에 오류를 방지하기 위하여 해당 key값을 remove 해준다.
 * 
 * Wrapper와 상속의 차이는 Wrapper일 경우 로직이 자유롭지만 get, put같은 메서드도 다 직접 작성해야 한다.
 * @author LE-P-2015-0005
 *
 * @param <K>
 * @param <V>
 */
public class ConcurrentHashMapWrapper<K, V> {
	private ConcurrentHashMap concurrentHashMap;
	
	public ConcurrentHashMapWrapper() {
		this.concurrentHashMap = new ConcurrentHashMap<>();
	}
	
	public Object put(K key, V value) {
		if (value == null) {
			this.concurrentHashMap.remove(key);
			return null;
		}
		return this.concurrentHashMap.put(key, value);
	}
	
	public Object get(K key) {
		return this.concurrentHashMap.get(key);
	}
	
	public static void main(String[] args) {
		CustomConcurrentHashMap<String, String> c1 = new CustomConcurrentHashMap<>();
		ConcurrentHashMapWrapper<String, String> c2 = new ConcurrentHashMapWrapper<>();
		c1.put("123", "abc");
		c2.put("123", "abc");
		
		System.err.println(c1.get("123"));
		System.err.println(c2.get("123"));
	}
}
