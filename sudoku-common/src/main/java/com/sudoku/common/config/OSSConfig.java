package com.sudoku.common.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSConfig {

  @Value("${alicloud.access-key-id}")
  private String accessKeyId;
  @Value("${alicloud.secret-key-secret}")
  private String secretKeySecret;
  @Value("${alicloud.oss.endpoint}")
  private String endpoint;

  @Bean
  public OSS oss() {
    return new OSSClientBuilder().build(endpoint, accessKeyId, secretKeySecret);
  }
}
