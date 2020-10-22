package com.sudoku.common.utils.project;

import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.exception.UserException;
import com.sudoku.project.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户工具类
 */
@Component
public class UserUtils {

  @Autowired
  private UserMapper userMapper;

  /**
   * 检查指定ID的用户是否存在
   *
   * @param id 用户ID
   */
  public void checkUserIdIsExist(Integer id) {
    if (!userIdIsExist(id)) {
      throw new UserException(StatusCode.USER_NOT_FOUND);
    }
  }

  /**
   * 检查指定ID的用户是否存在
   *
   * @param id 用户ID
   */
  public boolean userIdIsExist(Integer id) {
    return id != null && userMapper.selectById(id) != null;
  }
}
