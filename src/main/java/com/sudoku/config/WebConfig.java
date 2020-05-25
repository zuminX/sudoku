package com.sudoku.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * WebMvc的配置类
 */
@Configuration
public class WebConfig {

  /**
   * 添加NOT_FOUND的错误页面为首页
   *
   * @return Web服务器定制器
   */
  @Bean
  public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
    return factory -> {
      factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/index.html"));
    };
  }

}
