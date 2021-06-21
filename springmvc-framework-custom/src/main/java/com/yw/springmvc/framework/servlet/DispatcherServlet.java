package com.yw.springmvc.framework.servlet;

import com.yw.spring.framework.config.BeanDefinition;
import com.yw.spring.framework.factory.support.DefaultListableBeanFactory;
import com.yw.spring.framework.reader.XmlBeanDefinitionReader;
import com.yw.spring.framework.resource.ClasspathResource;
import com.yw.spring.framework.util.PropertiesLoaderUtils;
import com.yw.springmvc.framework.adapter.HandlerAdapter;
import com.yw.springmvc.framework.mapping.HandlerMapping;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * spring mvc 提供的唯一的一个Servlet类
 *
 * @author yangwei
 */
public class DispatcherServlet extends AbstractServlet {
    /**
     * HandlerMapping的策略集合
     */
    private List<HandlerMapping> handlerMappings = new ArrayList<>();
    /**
     * HandlerAdapter的策略集合
     */
    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    private DefaultListableBeanFactory beanFactory;

    private static Properties defaultStrategies;

    static {
        try {
            ClasspathResource resource = new ClasspathResource("DispatcherServlet.properties");
            defaultStrategies = PropertiesLoaderUtils.loadProperties(resource);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
//        handlerAdapters.add(new HttpRequestHandlerAdapter());
//        handlerAdapters.add(new SimpleControllerHandlerAdapter());
//
//        handlerMappings.add(new BeanNameUrlHandlerMapping());
//        handlerMappings.add(new SimpleUrlHandlerMapping());
        String location = config.getInitParameter("contextConfigLocation");
        // 初始化spring容器
        initContainer(location);
        // 初始化策略集合
        initStrategies();
    }

    private void initContainer(String location) {
        // 加载spring容器需要的BeanDefinition信息
        beanFactory = new DefaultListableBeanFactory();

        ClasspathResource resource = new ClasspathResource(location);
        InputStream inputStream = resource.getResource();

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions(inputStream);

        // 注册默认策略BeanDefinition
        registerDefaultStrategies();

        // 将spring容器中管理的所有单例Bean进行初始化(getBean)
        beanFactory.getBeansByType(Object.class);
    }

    private void registerDefaultStrategies() {
        registerDefaultStrategies(HandlerMapping.class);
        registerDefaultStrategies(HandlerAdapter.class);
    }

    private <T> void registerDefaultStrategies(Class<T> strategyInterface) {
        String key = strategyInterface.getName();
        String value = defaultStrategies.getProperty(key);
        if (value == null) {
            return;
        }
        String[] clazzNames = StringUtils.split(value, ",");
        for (String clazzName : clazzNames) {
            try {
                Class<?> clazzType = Class.forName(clazzName);
                String beanName = clazzType.getSimpleName();
                BeanDefinition beanDefinition = new BeanDefinition(clazzName, beanName);
                beanDefinition.setScope("singleton");
                beanFactory.registerBeanDefinition(beanName, beanDefinition);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void initStrategies() {
        initHandlerMappings();
        initHandlerAdapters();
    }

    private void initHandlerMappings() {
        handlerMappings = beanFactory.getBeansByType(HandlerMapping.class);
    }

    private void initHandlerAdapters() {
        handlerAdapters = beanFactory.getBeansByType(HandlerAdapter.class);
    }

    @Override
    public void doDispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1、根据请求，查找对应的处理类
            // 问题1：处理类长啥样？（它和Servlet无关，可以随便写，只是说为了统一，最后指定规范[接口]）
            // 问题2：去哪找处理类？（也就是请求URL和处理类的关系在哪建立）
            Object handler = getHandler(request);
            if (handler == null) {
                return;
            }
            // 2、调用处理类的方法，执行请求处理，并返回处理结果
            HandlerAdapter ha = getHandlerAdapter(handler);
            if (ha == null) {
                return;
            }
            ha.handleRequest(handler, request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Object getHandler(HttpServletRequest request) throws Exception {
        // 首先 处理类和请求之间 的映射关系可能存储在不同的地方（HandlerMapping）
        if (handlerMappings != null) {
            for (HandlerMapping hm : handlerMappings) {
                Object handler = hm.getHandler(request);
                if (handler != null) {
                    return handler;
                }
            }
        }
        return null;
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        if (handlerAdapters != null) {
            // 遍历策略集合
            for (HandlerAdapter ha : handlerAdapters) {
                if (ha.supports(handler)) {
                    return ha;
                }
            }
        }
        return null;
    }
}