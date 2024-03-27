package com.mysite.svp;

import com.mysite.svp.util.TimeUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public TimeUtil timeUtil() {
        return new TimeUtil();
    }
}
