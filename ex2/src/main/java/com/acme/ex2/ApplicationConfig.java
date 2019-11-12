package com.acme.ex2;


import com.acme.common.service.impl.CommandProcessorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@ComponentScan // pour les 3 handlers et applicationPoperties
@PropertySource("classpath:application.properties")
@Import(CommandProcessorImpl.class) // ce n'est pas la peine de mettre @componenet ou @service sur la class concerner
public class ApplicationConfig {
    private static ApplicationContext ctx;

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
