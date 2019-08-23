package kr.letech.webServer.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe한 HashMap.
 * ConcurrentHashMap를 상속 받아서 구현하였으며,
 * put 값이 null 인 경우에 오류를 방지하기 위하여 해당 key값을 remove 해준다.
 * @author LE-P-2015-0005
 *
 * @param <K>
 * @param <V>
 */
public class CustomConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> {
	@Override
	public V put(K key, V value) {
		if (value == null) {
			super.remove(key);
			return null;
		}
		return super.put(key, value);
	}
}
