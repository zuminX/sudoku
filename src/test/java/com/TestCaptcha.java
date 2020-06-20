package com;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import org.junit.jupiter.api.Test;

public class TestCaptcha {

  @Test
  void name() {
    LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(111, 36);
    String imageBase64 = lineCaptcha.getImageBase64();
    System.out.println(UUID.fastUUID());
    System.out.println(imageBase64);
    System.out.println(lineCaptcha.getCode());
  }
}
