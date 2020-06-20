package com.sudoku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.model.entity.User;

public interface UserMapper extends BaseMapper<User> {

  User findByUsername(String username);
}