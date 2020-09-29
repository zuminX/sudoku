package com.sudoku.project.convert;

import com.sudoku.project.model.entity.User;
import com.sudoku.project.model.vo.UserVO;
import org.mapstruct.Mapper;

/**
 * 用户转换器
 */
@Mapper
public interface UserConvert {

  /**
   * 将用户表对应的对象转换为显示层对象
   *
   * @param user 用户表对应的对象
   * @return 用户显示层对象
   */
  UserVO convert(User user);
}
