package com.acme.ex2;

import com.acme.common.service.impl.CommandProcessorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;

@ComponentScan
@Import(CommandProcessorImpl.class)
@EnableCaching
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy//(proxyTargetClass = true)
public class ApplicationConfig {

    @Bean
    CacheManager cacheManager(){
        return new ConcurrentMapCacheManager("commands");
    }
    @Bean
    @Scope("prototype")
    Logger logger(InjectionPoint ip){
        return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
    }
}
