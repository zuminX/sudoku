package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.entity.Resource;
import java.util.Set;
import org.apache.ibatis.annotations.Param;

public interface ResourceMapper extends BaseMapper<Resource> {

  Set<String> selectPermsByUserId(@Param("userId") Integer userId);
}