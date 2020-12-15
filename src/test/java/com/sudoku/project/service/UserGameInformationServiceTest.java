package com.sudoku.project.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.sudoku.project.convert.UserGameInformationConvert;
import com.sudoku.project.mapper.SudokuLevelMapper;
import com.sudoku.project.mapper.UserGameInformationMapper;
import com.sudoku.project.model.entity.UserGameInformation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import utils.GameRecordUtils;

/**
 * 用户游戏信息业务层实现类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class UserGameInformationServiceTest {

  private UserGameInformationService userGameInformationService;

  @Mock
  private UserGameInformationMapper userGameInformationMapper;

  @Mock
  private SudokuLevelMapper sudokuLevelMapper;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() {
    userGameInformationService = new UserGameInformationService(userGameInformationMapper, sudokuLevelMapper,
        Mappers.getMapper(UserGameInformationConvert.class));
  }

  @Test
  public void testUpdateUserGameInformation() {
    UserGameInformation beforeUpdateUserGameInformation = getUserGameInformation();
    UserGameInformation afterUpdateUserGameInformation = UserGameInformation.builder()
        .total(2)
        .correctNumber(2)
        .averageSpendTime(500_000)
        .minSpendTime(400_000)
        .maxSpendTime(600_000)
        .build();

    testUpdateUserGameInformation(beforeUpdateUserGameInformation, afterUpdateUserGameInformation);
  }

  @Test
  public void testUpdateUserGameInformationIfIsFirstGame() {
    UserGameInformation beforeUpdateUserGameInformation = UserGameInformation.builder().total(0).correctNumber(0).build();
    UserGameInformation afterUpdateUserGameInformation = UserGameInformation.builder()
        .total(1)
        .correctNumber(1)
        .averageSpendTime(600_000)
        .minSpendTime(600_000)
        .maxSpendTime(600_000)
        .build();

    testUpdateUserGameInformation(beforeUpdateUserGameInformation, afterUpdateUserGameInformation);
  }

  private void testUpdateUserGameInformation(UserGameInformation beforeUpdateUserGameInformation,
      UserGameInformation afterUpdateUserGameInformation) {
    when(userGameInformationMapper.selectByUserIdAndSudokuLevelId(anyInt(), anyInt())).thenReturn(beforeUpdateUserGameInformation);
    assertEquals(afterUpdateUserGameInformation, userGameInformationService.updateUserGameInformation(GameRecordUtils.getGameRecordBO()));
  }

  private UserGameInformation getUserGameInformation() {
    return UserGameInformation.builder()
        .total(1)
        .correctNumber(1)
        .averageSpendTime(400_000)
        .minSpendTime(400_000)
        .maxSpendTime(400_000)
        .build();
  }

}