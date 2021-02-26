package com.sudoku.system.controller;

import com.sudoku.common.core.domain.CommonResult;
import com.sudoku.system.model.body.LoginBody;
import com.sudoku.system.model.vo.LoginSuccessVO;
import com.sudoku.system.service.CaptchaService;
import com.sudoku.system.service.LoginService;
import com.sudoku.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
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

  private final LoginService loginService;

  private final CaptchaService captchaService;

  private final UserService userService;

  public LoginController(LoginService loginService, CaptchaService captchaService, UserService userService) {
    this.loginService = loginService;
    this.captchaService = captchaService;
    this.userService = userService;
  }

  @PostMapping("/login")
  @ApiOperation("登录")
  @ApiImplicitParam(name = "loginBody", value = "loginBody", dataTypeClass = LoginBody.class, required = true)
  public CommonResult<LoginSuccessVO> login(@RequestBody @Valid LoginBody loginBody) {
    captchaService.checkCaptcha(loginBody.getUuid(), loginBody.getCode());
    LoginSuccessVO login = loginService.login(loginBody);
    userService.updateRecentLoginTime(login.getUser().getId());
    //必须手动包装，否则会产生bug
    return CommonResult.success(login);
  }
}
