package com.acme.ex2.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@Aspect
@ManagedResource
public class HandlerPerformanceMonitor {

    private final Logger logger;
    private boolean enabled;

    public HandlerPerformanceMonitor(Logger logger) {
        this.logger = logger;
    }

    @ManagedAttribute
    public boolean isEnabled() {
        return enabled;
    }

    @ManagedAttribute
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    //@Around("execution(* com.acme.ex2.business..*.handle(..))")
    @Around("execution(* com.acme.common.business.CommandHandler.handle(..))")
    Object intercept(ProceedingJoinPoint pjp) throws Throwable {
        if(!this.enabled){
            return pjp.proceed();
        }
        long n = System.currentTimeMillis();
        // TODO appel du joinpoint
        Object proceed = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - n;
        // log pour indiquer le temps d'exécution de la méthode
        logger.info("it took {}ms to call {}", elapsedTime, pjp.toString());
        // TODO renvoi du résultat retourné par le joinpoint

        return proceed;
    }


}
