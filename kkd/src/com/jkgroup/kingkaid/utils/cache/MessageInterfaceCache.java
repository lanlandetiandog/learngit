package com.jkgroup.kingkaid.utils.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * MessageInterface缓存
 * @author pan
 * @Create Date 2015-03-27
 */
public class MessageInterfaceCache {

	private static final Map<String, String> cache = new HashMap<String,String>();
	
	/**
	 * 向缓存中增加MessageInterface
	 * @param key
	 * @param message
	 */
	public static void put(String key,String str){
		if(!cache.containsKey(key)){
			cache.put(key, str);
		}
	}
	
	/**
	 * 获取缓存中的MessageInterface
	 * @param key
	 * @return
	 */
	public static String get(String key){
		return cache.get(key);
	}
	
	/**
	 * 是否已缓存
	 * @param key
	 * @return
	 */
	public static boolean contains(String key){
		return cache.containsKey(key);
	}
	
	/**
	 * 删除缓存中的对象
	 * @param key
	 * @return
	 */
	public static void remove(String key){
		if(cache.containsKey(key)){
			cache.remove(key);
		}
	}
	
	/**
	 * 删除缓存中的对象
	 * @param key
	 * @return
	 */
	public static int size(){
		return cache.size();
	}
}
