package com.sudoku.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 配置Swagger接口文档
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  /**
   * 创建Rest的API
   *
   * @return Docket对象
   */
  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(this.apiInfo())
        .select()
        //设置controller的路径，获取api
        .apis(RequestHandlerSelectors.basePackage("com.sudoku.project.controller"))
        .paths(PathSelectors.any())
        .build();
  }

  /**
   * 创建API信息
   *
   * @return API信息
   */
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("数独项目")
        .description("数独项目API文档")
        .version("1.0.0")
        .contact(new Contact("zumin", "www.sudoku.com", "125287120@qq.com"))
        .build();
  }
}
