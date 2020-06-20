package com.sudoku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.model.bo.RankItemBO;import com.sudoku.model.entity.UserGameInformation;import java.util.List;import org.apache.ibatis.annotations.Param;

public interface UserGameInformationMapper extends BaseMapper<UserGameInformation> {

  List<UserGameInformation> selectByUid(@Param("uid") Integer uid);

  UserGameInformation selectByUidAndSlid(@Param("uid") Integer uid, @Param("slid") Integer slid);

  int batchInsertByUserIdAndSlids(@Param("userId") Integer userId, @Param("lackSlids") List<Integer> lackSlids);

  int insertDefaultByUserId(@Param("userId") Integer userId);

  int updateByUidAndSlid(@Param("updated") UserGameInformation updated, @Param("uid") Integer uid, @Param("slid") Integer slid);

  int updateTotalByUidAndSlid(@Param("updatedTotal") Integer updatedTotal, @Param("uid") Integer uid, @Param("slid") Integer slid);

  List<RankItemBO<Integer>> selectLimitNumberGroupBySlidOrderByAverageSpendTime(
      @Param("limitNumber") Integer limitNumber);

  List<RankItemBO<Integer>> selectLimitNumberGroupBySlidOrderByMinSpendTime(
      @Param("limitNumber") Integer limitNumber);

  List<RankItemBO<Integer>> selectLimitNumberGroupBySlidOrderByCorrectNumber(
      @Param("limitNumber") Integer limitNumber);
}