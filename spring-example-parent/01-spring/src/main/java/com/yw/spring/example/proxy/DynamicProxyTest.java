package com.yw.spring.example.proxy;

import com.yw.spring.example.proxy.factory.CgLibProxyFactory;
import com.yw.spring.example.proxy.factory.JDKProxyFactory;
import com.yw.spring.example.proxy.service.UserService;
import com.yw.spring.example.proxy.service.UserServiceImpl;
import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author yangwei
 */
public class DynamicProxyTest {
    @Test
    public void testJDKProxy(){
        // 1、创建目标对象
        UserService service = new UserServiceImpl();
        // 2、生成代理对象
        JDKProxyFactory proxyFactory = new JDKProxyFactory(service);
        // 得到代理对象
        UserService proxy = (UserService) proxyFactory.getProxy();
        // 生成class文件
        generatorClass(proxy);

        // 3、调用目标对象的方法
        service.saveUser();
        System.out.println("===============");
        // 4、调用代理对象的方法
        proxy.saveUser();
    }

    @Test
    public void testCgLibProxy() {
        // 创建目标对象
        UserService service = new UserServiceImpl();

        // 生成代理对象
        CgLibProxyFactory proxyFactory = new CgLibProxyFactory();
        UserService proxy = (UserService) proxyFactory.getProxy(service.getClass());

        // 调用目标对象的方法
        service.saveUser();
        System.out.println("===============");
        // 调用代理对象的方法
        proxy.saveUser();
    }

    private void generatorClass(Object proxy) {
        FileOutputStream out = null;
        try {
            // byte[] generateProxyClass =
            // ProxyGenerator.generateProxyClass(proxy.getClass().getName(), new Class[]
            // {proxy.getClass()});
            byte[] generateProxyClass = ProxyGenerator.generateProxyClass(proxy.getClass().getSimpleName(),
                    new Class[] { proxy.getClass() });
            out = new FileOutputStream(proxy.getClass().getSimpleName() + ".class");
            out.write(generateProxyClass);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }
            }
        }
    }
}
