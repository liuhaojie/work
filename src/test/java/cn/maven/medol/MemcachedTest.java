package cn.maven.medol;

import org.junit.Test;

import junit.framework.TestCase;

public class MemcachedTest extends TestCase {

	@Test
	public void testServerList(){
		MemcacheManger.setCache("liuhaojie", 1000, "it's my test!!!!!!!");
		Object obj = MemcacheManger.getCache("liuhaojie");
	}
}
