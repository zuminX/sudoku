package com.sudoku.mapper;

import com.sudoku.model.dto.RankItemDTO;
import com.sudoku.model.po.UserGameInformation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 用户游戏信息持久层
 */
public interface UserGameInformationMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(UserGameInformation record);

  int insertSelective(UserGameInformation record);

  UserGameInformation selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(UserGameInformation record);

  int updateByPrimaryKey(UserGameInformation record);

  List<UserGameInformation> selectByUid(@Param("uid") Integer uid);

  UserGameInformation selectByUidAndSlid(@Param("uid") Integer uid, @Param("slid") Integer slid);

  int batchInsertByUserIdAndSlids(@Param("userId") Integer userId, @Param("lackSlids") List<Integer> lackSlids);

  int insertDefaultByUserId(@Param("userId") Integer userId);

  int updateByUidAndSlid(@Param("updated") UserGameInformation updated, @Param("uid") Integer uid, @Param("slid") Integer slid);

  int updateTotalByUidAndSlid(@Param("updatedTotal") Integer updatedTotal, @Param("uid") Integer uid, @Param("slid") Integer slid);

  List<RankItemDTO<Integer>> selectLimitNumberGroupBySlidOrderByAverageSpendTime(
      @Param("limitNumber") Integer limitNumber);

  List<RankItemDTO<Integer>> selectLimitNumberGroupBySlidOrderByMinSpendTime(
      @Param("limitNumber") Integer limitNumber);

  List<RankItemDTO<Integer>> selectLimitNumberGroupBySlidOrderByCorrectNumber(
      @Param("limitNumber") Integer limitNumber);

}