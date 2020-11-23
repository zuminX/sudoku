package com.sudoku.common.utils.project;

import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.exception.UserException;
import com.sudoku.project.mapper.UserMapper;
import org.springframework.stereotype.Component;

/**
 * 用户工具类
 */
@Component
public class UserUtils {

  private final UserMapper userMapper;

  public UserUtils(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  /**
   * 检查指定ID的用户是否存在
   * 若不存在，则抛出UserException异常
   *
   * @param id 用户ID
   */
  public void checkUserIdExist(Integer id) {
    if (!existUserId(id)) {
      throw new UserException(StatusCode.USER_NOT_FOUND);
    }
  }

  /**
   * 检查指定ID的用户是否存在
   *
   * @param id 用户ID
   * @return 存在返回true，不存在返回false
   */
  public boolean existUserId(Integer id) {
    return id != null && userMapper.selectById(id) != null;
  }
}
