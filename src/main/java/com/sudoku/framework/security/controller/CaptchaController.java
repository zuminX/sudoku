package com.sudoku.framework.security.controller;

import com.sudoku.framework.security.model.CaptchaVO;
import com.sudoku.framework.security.service.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
@Api(tags = "验证码API接口")
public class CaptchaController {

  private final CaptchaService captchaService;

  public CaptchaController(CaptchaService captchaService) {
    this.captchaService = captchaService;
  }

  @GetMapping("/captchaImage")
  @ApiOperation("获取验证码")
  public CaptchaVO getCaptcha() {
    return captchaService.generateCaptcha();
  }
}
