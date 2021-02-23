package com.zsw.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZhangShaowei on 2021/2/10 11:32
 */
@Slf4j
public class JdkProxy implements InvocationHandler {

    private Object target;

    private final Map<Method, MethodHandle> dispatcher = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getInstance(T target) {
        this.target = target;
        Class<?> clazz = target.getClass();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(target, args);
        }
        log.info("before: {}", method.getName());
        Object invoke;
        if (method.isDefault()) {
            invoke = this.dispatcher.computeIfAbsent(method, m -> {
                try {
                    return MethodHandles.lookup()
                            .unreflectSpecial(method, method.getDeclaringClass())
                            .bindTo(proxy);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }).invokeWithArguments(args);
        } else {
            invoke = method.invoke(target, args);
        }
        log.info("after: {}, result = {}", method.getName(), invoke);
        return invoke;
    }
}
