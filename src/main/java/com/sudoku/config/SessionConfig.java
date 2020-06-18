package com.sudoku.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Session配置类
 */
@Configuration
//启动Redis保存Session，并将最大无效时间设为4小时
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 14400)
public class SessionConfig {

}
