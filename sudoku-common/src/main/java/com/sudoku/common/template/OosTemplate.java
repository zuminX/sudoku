package com.sudoku.common.template;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class OosTemplate {

  @Value("${alicloud.oss.endpoint}")
  private String endpoint;
  @Value("${alicloud.oss.bucket}")
  private String bucket;
  @Value("${alicloud.access-key}")
  private String accessKey;
  @Value("${alicloud.secret-key}")
  private String secretKey;

  public String upload(MultipartFile file, String fileName) throws IOException {
    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKey, secretKey);
    PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, file.getInputStream());
    ossClient.putObject(putObjectRequest);
    ossClient.shutdown();
    return "https://" + bucket + "." + endpoint + "/" + fileName;
  }
}
