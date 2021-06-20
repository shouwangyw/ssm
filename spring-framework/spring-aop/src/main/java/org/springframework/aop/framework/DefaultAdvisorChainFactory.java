/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.aop.framework;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aopalliance.intercept.Interceptor;
import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.aop.Advisor;
import org.springframework.aop.IntroductionAdvisor;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import org.springframework.aop.support.MethodMatchers;
import org.springframework.lang.Nullable;

/**
 * A simple but definitive way of working out an advice chain for a Method,
 * given an {@link Advised} object. Always rebuilds each advice chain;
 * caching can be provided by subclasses.
 *
 * @author Juergen Hoeller
 * @author Rod Johnson
 * @author Adrian Colyer
 * @since 2.0.3
 */
@SuppressWarnings("serial")
public class DefaultAdvisorChainFactory implements AdvisorChainFactory, Serializable {

	@Override
	public List<Object> getInterceptorsAndDynamicInterceptionAdvice(
			Advised config, Method method, @Nullable Class<?> targetClass) {

		// This is somewhat tricky... We have to process introductions first,
		// but we need to preserve order in the ultimate list.
		// 返回值集合，里面装的都是Interceptor或者它的子接口MethodInterceptor
		List<Object> interceptorList = new ArrayList<Object>(config.getAdvisors().length);
		// 获取目标类的类型
		Class<?> actualClass = (targetClass != null ? targetClass : method.getDeclaringClass());
		// 是否有引介
		boolean hasIntroductions = hasMatchingIntroductions(config, actualClass);
		// advisor适配器注册中心
		// MethodBeforeAdviceAdapter:将Advisor适配成MethodBeforeAdvice
		// AfterReturningAdviceAdapter:将Advisor适配成AfterReturningAdvice
		// ThrowsAdviceAdapter:将Advisor适配成ThrowsAdvice
		AdvisorAdapterRegistry registry = GlobalAdvisorAdapterRegistry.getInstance();
		// 去产生代理对象的过程中，针对该目标方法获取到的所有合适的Advisor集合
		for (Advisor advisor : config.getAdvisors()) {
			if (advisor instanceof PointcutAdvisor) {
				// Add it conditionally.
				PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
				// 如果该Advisor可以对目标类进行增强，则进行后续操作
				if (config.isPreFiltered() || pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)) {
					// 获取方法匹配器，该方法匹配器可以根据指定的切入点表达式进行方法匹配
					MethodMatcher mm = pointcutAdvisor.getPointcut().getMethodMatcher();
					// 使用方法匹配器工具类进行方法匹配
					if (MethodMatchers.matches(mm, method, actualClass, hasIntroductions)) {
						// 将advisor转成MethodInterceptor
						MethodInterceptor[] interceptors = registry.getInterceptors(advisor);
						// MethodMatcher接口通过重载定义了两个matches()方法
						// 两个参数的matches()被称为静态匹配，在匹配条件不是太严格时使用，可以满足大部分场景的使用
						// 称之为静态的主要是区分为三个参数的matches()方法需要在运行时动态的对参数的类型进行匹配;
						// 两个方法的分界线就是boolean isRuntime()方法
						// 进行匹配时先用两个参数的matches()方法进行匹配，若匹配成功，则检查boolean isRuntime()的返回值
						// 若为true，则调用三个参数的matches()方法进行匹配(若两个参数的都匹配不中，三个参数的必定匹配不中)

						// 需要根据参数动态匹配
						if (mm.isRuntime()) {
							// Creating a new object instance in the getInterceptors() method
							// isn't a problem as we normally cache created chains.
							for (MethodInterceptor interceptor : interceptors) {
								interceptorList.add(new InterceptorAndDynamicMethodMatcher(interceptor, mm));
							}
						}
						else {
							interceptorList.addAll(Arrays.asList(interceptors));
						}
					}
				}
			}
			else if (advisor instanceof IntroductionAdvisor) {
				IntroductionAdvisor ia = (IntroductionAdvisor) advisor;
				if (config.isPreFiltered() || ia.getClassFilter().matches(actualClass)) {
					Interceptor[] interceptors = registry.getInterceptors(advisor);
					interceptorList.addAll(Arrays.asList(interceptors));
				}
			}
			else {
				// 通过AdvisorAdapterRegistry将Advisor都适配成MethodInterceptor类型
				Interceptor[] interceptors = registry.getInterceptors(advisor);
				interceptorList.addAll(Arrays.asList(interceptors));
			}
		}

		return interceptorList;
	}

	/**
	 * Determine whether the Advisors contain matching introductions.
	 */
	private static boolean hasMatchingIntroductions(Advised config, Class<?> actualClass) {
		for (Advisor advisor : config.getAdvisors()) {
			if (advisor instanceof IntroductionAdvisor) {
				IntroductionAdvisor ia = (IntroductionAdvisor) advisor;
				if (ia.getClassFilter().matches(actualClass)) {
					return true;
				}
			}
		}
		return false;
	}

}
