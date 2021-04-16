package com.sudoku.system.controller;

import com.sudoku.common.config.SudokuConfig;
import com.sudoku.common.dto.OOSCallbackDTO;
import com.sudoku.common.dto.OOSPolicyDTO;
import com.sudoku.common.template.OSSTemplate;
import com.sudoku.system.model.bo.LoginUserBO;
import com.sudoku.system.security.service.UserTokenService;
import com.sudoku.system.service.UserService;
import com.sudoku.system.utils.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/user/profile")
public class UserProfileController {

  private final UserService userService;

  private final UserTokenService tokenService;

  private final OSSTemplate ossTemplate;

  @Value("${alicloud.oss.max-size.avatar}")
  private long avatarMaxSize;

  public UserProfileController(UserService userService, UserTokenService tokenService, OSSTemplate ossTemplate) {
    this.userService = userService;
    this.tokenService = tokenService;
    this.ossTemplate = ossTemplate;
  }

  @PostMapping("/uploadAvatarPolicy")
  @ApiOperation("获取上传头像的凭证")
  public OOSPolicyDTO getUploadAvatarPolicy() {
    return ossTemplate.policy(avatarMaxSize, SudokuConfig.getAvatarDir(), "user/profile/updateAvatar");
  }


  @PostMapping("/updateAvatar")
  @ApiOperation("更新头像")
  public void updateAvatar() {
    OOSCallbackDTO oosCallback = ossTemplate.resolveCallbackData();
    String avatarPath = oosCallback.getFilePath();
    userService.updateAvatar(avatarPath);
    updateCurrentUserAvatar(avatarPath);
  }

  /**
   * 更新缓存用户头像
   *
   * @param avatarPath 头像地址
   */
  private void updateCurrentUserAvatar(String avatarPath) {
    LoginUserBO loginUser = SecurityUtils.getLoginUser();
    loginUser.getUser().setAvatar(avatarPath);
    tokenService.setLoginUser(loginUser);
  }
}
