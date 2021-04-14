package com.sudoku.system.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.sudoku.common.config.SudokuConfig;
import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.core.domain.CommonResult;
import com.sudoku.common.exception.FileException;
import com.sudoku.common.utils.file.FileUtils;
import com.sudoku.common.utils.file.MimeTypeUtils;
import com.sudoku.system.model.bo.LoginUserBO;
import com.sudoku.system.security.service.UserTokenService;
import com.sudoku.system.service.UserService;
import com.sudoku.system.utils.SecurityUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user/profile")
@Validated
public class UserProfileController {

  private final UserService userService;

  private final UserTokenService tokenService;

  public UserProfileController(UserService userService, UserTokenService tokenService) {
    this.userService = userService;
    this.tokenService = tokenService;
  }

  @PostMapping("/updateAvatar")
  @ApiOperation("更新头像")
  @ApiImplicitParam(name = "avatar", value = "头像文件", dataTypeClass = MultipartFile.class, required = true)
  public CommonResult<String> updateAvatar(@RequestParam MultipartFile avatar) {
    if (avatar.isEmpty()) {
      throw new FileException(StatusCode.FILE_EMPTY_UPLOAD);
    }
    try {
      String avatarPath = FileUtils.upload(SudokuConfig.getAvatarPath(), avatar, MimeTypeUtils.IMAGE_EXTENSION);
      userService.updateAvatar(avatarPath);
      removeOldAvatar();
      updateCurrentUserAvatar(avatarPath);
      //需要手动包装返回值
      return CommonResult.success(avatarPath);
    } catch (IOException e) {
      throw new FileException(e);
    }
  }

  /**
   * 删除用户的旧头像
   */
  private void removeOldAvatar() {
    String oldAvatarPath = SecurityUtils.getCurrentUser().getAvatar();
    if (StrUtil.isNotBlank(oldAvatarPath)) {
      FileUtil.del(FileUtils.getAbsolutePath(SudokuConfig.getAvatarPath(), oldAvatarPath));
    }
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
