package com.sudoku.security.controller;

import com.sudoku.model.vo.CommonResult;
import com.sudoku.security.model.LoginBody;
import com.sudoku.security.model.LoginSuccessVO;
import com.sudoku.security.service.CaptchaService;
import com.sudoku.security.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/security")
@Api(tags = "登录API接口")
public class LoginController {

  @Autowired
  private LoginService loginService;
  @Autowired
  private CaptchaService captchaService;

  @PostMapping("/login")
  @ApiOperation("登录")
  @ApiImplicitParam(name = "loginBody", value = "loginBody", dataTypeClass = LoginBody.class, required = true)
  public CommonResult<LoginSuccessVO> login(@RequestBody @Valid LoginBody loginBody) {
    captchaService.checkCaptcha(loginBody.getUuid(), loginBody.getCode());
    //必须手动包装，否则会产生bug
    return CommonResult.success(loginService.login(loginBody));
  }
}
