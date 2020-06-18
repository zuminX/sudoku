package com.sudoku.convert;

import com.sudoku.model.entity.User;
import com.sudoku.model.vo.RegisterUserVO;
import com.sudoku.model.vo.UserVO;
import com.sudoku.utils.CoreUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户转换器
 */
@Mapper
public interface UserConvert {

  UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

  /**
   * 将用户表对应的对象转换为显示层对象
   *
   * @param user 用户表对应的对象
   * @return 用户显示层对象
   */
  UserVO convert(User user);

  /**
   * 将注册用户信息对象转换为用户表对应的对象
   *
   * @param registerUserVO 注册用户信息对象
   * @return 用户表对应的对象
   */
  default User convert(RegisterUserVO registerUserVO) {
    User user = new User();
    user.setUsername(registerUserVO.getUsername().trim());
    //对密码进行加密
    user.setPassword(CoreUtils.passwordEncoder().encode(registerUserVO.getPassword()));
    user.setNickname(registerUserVO.getNickname());
    return user;
  }
}
