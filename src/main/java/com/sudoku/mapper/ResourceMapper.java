package com.sudoku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.model.entity.Resource;
import java.util.Set;
import org.apache.ibatis.annotations.Param;

public interface ResourceMapper extends BaseMapper<Resource> {

  Set<String> selectPermsByUserId(@Param("userId") Integer userId);
}