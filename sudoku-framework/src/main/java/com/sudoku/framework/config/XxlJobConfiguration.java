package com.sudoku.framework.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * XXL-JOB任务调度平台的配置类
 */
@Configuration
public class XxlJobConfiguration {

  @Value("${xxl.job.admin.addresses}")
  private String adminAddresses;
  @Value("${xxl.job.accessToken}")
  private String accessToken;
  @Value("${xxl.job.executor.appname}")
  private String appName;

  /**
   * 使用配置文件的信息创建XxlJobSpringExecutor执行器
   *
   * @return 执行器对象
   */
  @Bean
  public XxlJobSpringExecutor xxlJobExecutor() {
    // 创建 XxlJobSpringExecutor 执行器
    XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
    xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
    xxlJobSpringExecutor.setAppname(appName);
    xxlJobSpringExecutor.setAccessToken(accessToken);
    return xxlJobSpringExecutor;
  }
}
