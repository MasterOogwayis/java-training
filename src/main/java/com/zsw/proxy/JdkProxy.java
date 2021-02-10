package com.zsw.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ZhangShaowei on 2021/2/10 11:32
 */
@Slf4j
public class JdkProxy implements InvocationHandler {

    private Object target;

    private MethodHandle methodHandle;

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
            if (this.methodHandle == null) {
                this.methodHandle = MethodHandles.lookup()
                        .unreflectSpecial(method, method.getDeclaringClass())
                        .bindTo(proxy);
            }
            invoke = this.methodHandle.invokeWithArguments(args);
        } else {
            invoke = method.invoke(target, args);
        }
        log.info("after: {}, result = {}", method.getName(), invoke);
        return invoke;
    }
}
