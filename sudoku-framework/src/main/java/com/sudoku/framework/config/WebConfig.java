package com.sudoku.framework.config;

import com.sudoku.framework.convert.StatisticsDateRangeMessageConvert;
import java.util.List;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc的配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(convert());
  }

  @Bean
  public StatisticsDateRangeMessageConvert convert() {
    return new StatisticsDateRangeMessageConvert();
  }

  /**
   * 添加NOT_FOUND的错误页面为首页
   *
   * @return Web服务器定制器
   */
  @Bean
  public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
    return factory -> factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/index.html"));
  }

}
