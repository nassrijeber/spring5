package com.acme.ex1;

import com.acme.ex1.dao.MovieDao;
import com.acme.ex1.dao.impl.FoxMovieDaoImpl;
import com.acme.ex1.dao.impl.WarnerMovieDaoImpl;
import com.acme.ex1.model.Movie;
import com.acme.ex1.service.MovieService;
import com.acme.ex1.service.impl.MovieServiceImpl;
import com.acme.ex1.service.impl.SuperMovieServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationFactory {

    private static Map<String, Object> beans = new HashMap<>();

    private static <T> T createProxy(T impl) {
        ClassLoader classLoader = impl.getClass().getClassLoader();
        Class<?>[] interfaces = impl.getClass().getInterfaces();
        InvocationHandler ih = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(impl, args);
                }
                long n = System.currentTimeMillis();
                Object ret = method.invoke(impl, args);
                long elaspedTime = System.currentTimeMillis() - n;
                System.out.println("it took " + elaspedTime + "ms to call " + method.getName() + " on " + impl.toString());
                return ret;
            }
        };
        Object proxy = Proxy.newProxyInstance(classLoader, interfaces, ih);
        return (T) proxy;
    }

    static {
        MovieDao fox = createProxy(new FoxMovieDaoImpl());
        MovieDao warner = createProxy(new WarnerMovieDaoImpl());
        MovieService service1 = createProxy(new MovieServiceImpl(fox));
        MovieService service2 = createProxy(new MovieServiceImpl(warner));
        MovieService superService = createProxy(new SuperMovieServiceImpl(Set.of(fox, warner)));
        beans.put("fox", fox);
        beans.put("warner", warner);
        beans.put("service1", service1);
        beans.put("service2", service2);
        beans.put("superService", superService);
    }

    public static Object getBean(String name) {
        Object bean = beans.get(name);
        if (bean == null) {
            throw new IllegalArgumentException("no bean named " + name);
        }
        return bean;
    }
}
