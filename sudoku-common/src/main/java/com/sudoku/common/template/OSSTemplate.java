package com.sudoku.common.template;

import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.sudoku.common.dto.OOSCallbackDTO;
import com.sudoku.common.dto.OOSPolicyDTO;
import com.sudoku.common.tools.ServletUtils;
import com.sudoku.common.utils.DateUtils;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OSSTemplate {

  private final OSS oss;
  @Value("${alicloud.access-key-id}")
  private String accessKeyId;
  @Value("${alicloud.oss.endpoint}")
  private String endpoint;
  @Value("${alicloud.oss.bucket-name}")
  private String bucketName;
  @Value("${alicloud.oss.policy.expire}")
  private int expire;
  @Value("${sudoku.addr}")
  private String addr;
  @Value("${token.header}")
  private String header;

  public OSSTemplate(OSS oss) {
    this.oss = oss;
  }

  public OOSPolicyDTO policy(long maxSize, String baseDir, String callbackPath) {
    String dir = getDateDir(baseDir);
    String postPolicy = oss.generatePostPolicy(getExpiration(), getPolicyConditions(maxSize * 1048576, dir));

    OOSPolicyDTO oosPolicy = new OOSPolicyDTO();
    oosPolicy.setAccessKeyId(accessKeyId);
    oosPolicy.setPolicy(getPolicy(postPolicy));
    oosPolicy.setSignature(oss.calculatePostSignature(postPolicy));
    oosPolicy.setDir(dir);
    oosPolicy.setHost(getHost());
    oosPolicy.setCallback(getCallbackData(callbackPath));
    return oosPolicy;
  }

  public OOSCallbackDTO resolveCallbackData() {
    HttpServletRequest request = ServletUtils.getRequest();
    OOSCallbackDTO oosCallback = new OOSCallbackDTO();
    oosCallback.setFilePath(getHost() + "/" + request.getParameter("filename"));
    oosCallback.setSize(request.getParameter("size"));
    oosCallback.setMimeType(request.getParameter("mimeType"));
    return oosCallback;
  }

  private String getPolicy(String postPolicy) {
    byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
    return BinaryUtil.toBase64String(binaryData);
  }

  private String getDateDir(String baseDir) {
    return baseDir + "/" + DateUtils.plainDateStr() + "/";
  }

  private Date getExpiration() {
    return new Date(System.currentTimeMillis() + expire * 1000L);
  }

  private String getHost() {
    return "https://" + bucketName + "." + endpoint;
  }

  private String getCallbackData(String callbackPath) {
    Map<String, String> headerMap = new HashMap<>();
    headerMap.put(header, ServletUtils.getRequest().getHeader(header));

    CallbackData callbackData = new CallbackData();
    callbackData.setCallbackUrl(addr + "/" + callbackPath);
    callbackData.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}");
    callbackData.setCallbackBodyType("application/x-www-form-urlencoded");
    callbackData.setSignatureVersion("2.0");
    callbackData.setAdditionalHeaders(headerMap);

    return BinaryUtil.toBase64String(JSONUtil.parse(callbackData).toString().getBytes());
  }

  private PolicyConditions getPolicyConditions(long maxSize, String dir) {
    PolicyConditions policyConditions = new PolicyConditions();
    policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
    policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
    return policyConditions;
  }

  @Data
  private static class CallbackData {

    private String callbackUrl;
    private String callbackBody;
    private String callbackBodyType;
    private String signatureVersion;
    private Map<String, String> additionalHeaders;
  }

}
