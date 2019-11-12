package com.acme.ex2;

import com.acme.common.service.impl.CommandProcessorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;

@ComponentScan // pour les 3 handlers et applicationPoperties
@PropertySource("classpath:application.properties")
@Import(CommandProcessorImpl.class)
//@EnableCaching
@EnableAspectJAutoProxy//(proxyTargetClass = true)
@EnableMBeanExport
public class ApplicationConfig {

    @Bean
    CacheManager cacheManager(){
        return new ConcurrentMapCacheManager("commands");
    }

    @Bean
    @Scope("prototype")
    Logger logger(InjectionPoint ip) {
        return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
    }

//    public static void main(String args[]) {
//        ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
//        Environment env = ctx.getEnvironment();
//        System.out.println(env.getProperty("folder"));
//    }

    public static void main(String[] args) {

    }
}
