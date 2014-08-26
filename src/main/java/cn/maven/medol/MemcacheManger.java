package cn.maven.medol;

import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class MemcacheManger {
	private  static MemcachedClient client = null;
	
	public synchronized static MemcachedClient getMemcachedClient(){
		if(client == null){
			try {
				MemcachedClientBuilder builder = new XMemcachedClientBuilder(
						AddrUtil.getAddresses("192.168.6.195:10001"));
				client = builder.build();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return client;
	}
	
	public static Object getCache(String key) {
		// TODO Auto-generated method stub
		client = getMemcachedClient();
		System.out.println("MemcacheManger  getCache");
		Object valueObject = null;
		try {
			valueObject = client.get(key);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return valueObject;
	}

	public static void setCache(String key, int time, Object value) {
		// TODO Auto-generated method stub
		client = getMemcachedClient();
		System.out.println("MemcacheManger  setCache");
		try {
			client.set(key, time, value);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
