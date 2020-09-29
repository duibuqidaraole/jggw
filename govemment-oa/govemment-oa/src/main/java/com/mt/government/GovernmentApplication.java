package com.mt.government;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/17 10:54
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.mt.government.mapper")
public class GovernmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(GovernmentApplication.class, args);
    }
}
