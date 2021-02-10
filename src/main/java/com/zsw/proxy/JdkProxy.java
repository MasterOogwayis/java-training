package com.zsw.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ZhangShaowei on 2021/2/10 11:32
 */
@Slf4j
public class JdkProxy implements InvocationHandler {

    private Object target;

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
            Field field = Lookup.class.getDeclaredField("IMPL_LOOKUP");
            field.setAccessible(true);
            Lookup lookup = (Lookup) field.get(null);
            // 15 = Lookup#ALL_MODES
            invoke = lookup
                    .unreflectSpecial(method, method.getDeclaringClass())
                    .bindTo(proxy)
                    .invokeWithArguments(args);
        } else {
            invoke = method.invoke(target, args);
        }
        log.info("after: {}, result = {}", method.getName(), invoke);
        return invoke;
    }
}
