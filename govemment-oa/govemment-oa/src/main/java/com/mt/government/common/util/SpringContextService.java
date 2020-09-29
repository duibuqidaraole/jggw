package com.mt.government.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring上下文
 *
 * @author g
 * @date 2019-11-04 9:18
 */
@Component
public class SpringContextService implements ApplicationContextAware {
    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }
}
