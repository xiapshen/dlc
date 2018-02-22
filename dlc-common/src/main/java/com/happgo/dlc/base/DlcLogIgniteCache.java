/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年7月8日 下午2:57:24
*
* @Package com.happgo.dlc.base 
* @Title: DlcLogIgniteCache.java
* @Description: DlcLogIgniteCache.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happgo.dlc.base;

import java.util.concurrent.TimeUnit;

import javax.cache.CacheException;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteClientDisconnectedException;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.eviction.fifo.FifoEvictionPolicy;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;

/**
 * ClassName:DlcLogIgniteCache
 * @deprecated
 * @Description: DlcLogIgniteCache.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年7月8日 下午2:57:24
 */
@SuppressWarnings("all")
public class DlcLogIgniteCache<K, V> {
	
	/**
	 * Ignite the ignite 
	 */
	private Ignite ignite;
	
	/**
	 * IgniteCache<K,V> the igniteCache 
	 */
	private IgniteCache<K, V> igniteCache;
	
	/**
	 * Constructor com.happygo.dlc.ignite.cache.DlcLogIgniteCache
	 * 描述：该构造函数默认为堆内缓存
	 * @param ignite
	 * @param cacheName
	 * @param duration
	 */
	public DlcLogIgniteCache(Ignite ignite, String cacheName, int duration) {
		CacheConfiguration cfg = new CacheConfiguration(cacheName);
		cfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
		cfg.setBackups(1);
        // 堆内缓存最近最少使用删除策略，参数1000表示堆内最多存储1000条记录
		cfg.setEvictionPolicy(new LruEvictionPolicy(1000));
        // 设置缓存过期时间
		cfg.setExpiryPolicyFactory(CreatedExpiryPolicy
				.factoryOf(new Duration(TimeUnit.MINUTES, duration)));
		this.ignite = ignite;
        this.igniteCache = ignite.getOrCreateCache(cfg);
	}
	
	/**
	 * Constructor com.happygo.dlc.ignite.cache.DlcLogIgniteCache
	 * 描述：该构造函数用户可以自定义缓存配置策略
	 * @param ignite
	 * @param cfg
	 */
	public DlcLogIgniteCache(Ignite ignite, CacheConfiguration cfg) {
		this.ignite = ignite;
		this.igniteCache = ignite.getOrCreateCache(cfg);
	}
	
	/**
	* @MethodName: put
	* @Description: 缓存，如果发现
	* @param key
	* @param val
	*/
	public void put(K key, V val) {
		try {
			igniteCache.put(key, val);
		} catch (CacheException e) {
			if (e.getCause() instanceof IgniteClientDisconnectedException) {
				IgniteClientDisconnectedException cause = (IgniteClientDisconnectedException) e
						.getCause();
				// Wait for reconnection.
				cause.reconnectFuture().get();
				igniteCache.put(key, val);
			}
		}
	}

	/**
	* @MethodName: get
	* @Description: the get
	* @param key
	* @return V
	*/
	public V get(K key) {
		return igniteCache.get(key);
	}
}
