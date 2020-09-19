package com.sudoku.framework.security.controller;

import com.sudoku.project.model.vo.CommonResult;
import com.sudoku.framework.security.model.LoginBody;
import com.sudoku.framework.security.model.LoginSuccessVO;
import com.sudoku.framework.security.service.CaptchaService;
import com.sudoku.framework.security.service.LoginService;
import com.sudoku.project.service.UserService;
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
  @Autowired
  private UserService userService;

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
