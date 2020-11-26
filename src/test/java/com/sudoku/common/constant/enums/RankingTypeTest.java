package com.sudoku.common.constant.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * 排行类型类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class RankingTypeTest {

  /**
   * 排行类型名为空时，查询结果应是null
   */
  @Test
  public void testFindByNameWithEmpty() {
    assertNull(RankingType.findByName(""));
    assertNull(RankingType.findByName(null));
  }

  /**
   * 测试根据名字查找对应的排行类型对象
   */
  @Test
  public void testFindByName() {
    RankingType rankingType = RankingType.CORRECT_NUMBER;
    RankingType findType = RankingType.findByName(rankingType.getName());
    assertEquals(rankingType, findType);
  }

  /**
   * 所有排行类型名的个数应与类型个数相同
   */
  @Test
  public void testGetAllRankingTypeName() {
    int allTypeNameSize = RankingType.getAllRankingTypeName().size();
    int typeSize = RankingType.values().length;
    assertEquals(typeSize, allTypeNameSize);
  }
}