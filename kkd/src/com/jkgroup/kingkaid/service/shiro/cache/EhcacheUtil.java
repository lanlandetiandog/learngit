package com.jkgroup.kingkaid.service.shiro.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

/**
 * 密码输入次数限制缓存
 *
 * @author pan
 * @create data 2015-03-24
 */
public class EhcacheUtil {

    private static final CacheManager cacheManager = CacheManager.getInstance();

    /**
     * 创建ehcache缓存，创建之后的有效期是3分钟
     */

    private static Cache cache = new Cache(new CacheConfiguration("systemCache", 5000).memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.FIFO).timeoutMillis(300).timeToLiveSeconds(60 * 3));

    static {
        cacheManager.addCache(cache);
    }

    public static void putItem(String key, Object item) {
        if (cache.get(key) != null) {
            cache.remove(key);
        }
        Element element = new Element(key, item);
        cache.put(element);
    }

    public static void removeItem(String key) {
        cache.remove(key);
    }

    public static void updateItem(String key, Object value) {
        putItem(key, value);
    }

    public static Object getItem(String key) {
        Element element = cache.get(key);
        return element == null ? null : element.getObjectValue();
    }
}
