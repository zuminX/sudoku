package com.sudoku.security.service.impl;

import static com.sudoku.constant.enums.StatusCode.CAPTCHA_EXPIRED;
import static com.sudoku.constant.enums.StatusCode.CAPTCHA_NOT_EQUALS;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import com.sudoku.constant.consist.RedisKeys;
import com.sudoku.exception.CaptchaException;
import com.sudoku.security.model.CaptchaVO;
import com.sudoku.security.service.CaptchaService;
import com.sudoku.utils.RedisUtils;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * 验证码业务层实现类
 */
@Data
@Service
@ConfigurationProperties("captcha")
public class CaptchaServiceImpl implements CaptchaService {

  /**
   * 验证码的宽度
   */
  private int width = 112;

  /**
   * 验证码的高度
   */
  private int height = 36;

  /**
   * 验证码字符的个数
   */
  private int codeNumber = 4;

  /**
   * 验证码干扰线的个数
   */
  private int lineCount = 100;

  /**
   * 验证码有效期(分钟)
   */
  private int expireTime;

  @Autowired
  private RedisUtils redisUtils;

  /**
   * 生成验证码
   *
   * @return 验证码对象
   */
  @Override
  public CaptchaVO generateCaptcha() {
    LineCaptcha captcha = CaptchaUtil.createLineCaptcha(width, height, codeNumber, lineCount);
    String uuid = UUID.fastUUID().toString();
    redisUtils.setObject(getCaptchaKey(uuid), captcha.getCode(), expireTime, TimeUnit.MINUTES);
    return new CaptchaVO(uuid, captcha.getImageBase64());
  }

  /**
   * 检查验证码是否正确
   *
   * @param uuid 唯一标识
   * @param code 待验证的码
   */
  @Override
  public void checkCaptcha(String uuid, String code) {
    String key = getCaptchaKey(uuid);
    String captcha = redisUtils.getObject(key);
    if (captcha == null) {
      throw new CaptchaException(CAPTCHA_EXPIRED);
    }
    redisUtils.deleteObject(key);
    if (!captcha.equalsIgnoreCase(code)) {
      throw new CaptchaException(CAPTCHA_NOT_EQUALS);
    }
  }

  /**
   * 获取验证码的key值
   *
   * @return 验证码在redis中的key值
   */
  private String getCaptchaKey(String uuid) {
    return RedisKeys.CAPTCHA_PREFIX + uuid;
  }
}