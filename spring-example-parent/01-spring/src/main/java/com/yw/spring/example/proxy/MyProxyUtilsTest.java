package com.yw.spring.example.proxy;

import com.yw.spring.example.proxy.service.UserService;
import com.yw.spring.example.proxy.service.UserServiceImpl;
import com.yw.spring.example.proxy.util.MyProxyUtils;
import org.junit.Test;

/**
 * @author yangwei
 * @date 2021-06-20 16:51
 */
public class MyProxyUtilsTest {
    /**
     * 测试JDK动态代理
     */
	@Test
	public void testJDKDynamicProxy() throws Exception{
		UserService service = new UserServiceImpl();
		service.saveUser();
		System.out.println("================");
		service = MyProxyUtils.getProxy(service);
		service.saveUser();
	}

    /**
     * 测试CGLIB动态代理
     */
    @Test
    public void testCGLIBDynamicProxy() throws Exception {
        UserService service = new UserServiceImpl();
        service.saveUser();
        System.out.println("================");
        service = MyProxyUtils.getProxy();
        service.saveUser();
    }
}
