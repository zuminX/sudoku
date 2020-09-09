package com.sudoku.security.service;

import com.sudoku.security.model.CaptchaVO;

/**
 * 验证码业务层接口
 */
public interface CaptchaService {

  /**
   * 生成验证码
   *
   * @return 验证码对象
   */
  CaptchaVO generateCaptcha();

  /**
   * 检查验证码是否正确
   *
   * @param uuid 唯一标识
   * @param code 待验证的码
   */
  void checkCaptcha(String uuid, String code);
}
