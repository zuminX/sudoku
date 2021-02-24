package com.sudoku.system.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.sudoku.common.exception.UserException;
import com.sudoku.system.mapper.UserMapper;
import com.sudoku.system.model.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * 用户工具类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class UserUtilsTest {

  @Mock
  private UserMapper userMapper;

  private UserUtils userUtils;

  /**
   * 用户ID列表
   */
  private List<Integer> idList;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() {
    idList = new ArrayList<>();
    IntStream.range(1, 10).forEach(i -> idList.add(i));

    userUtils = new UserUtils(userMapper);

    when(userMapper.selectById(anyInt())).thenAnswer(answer -> {
      Integer id = answer.getArgument(0);
      return idList.contains(id) ? User.builder().id(id).build() : null;
    });
  }

  /**
   * 使用存在用户的ID测试判断该ID对应的用户是否存在
   */
  @Test
  public void testExistUserIdIfExist() {
    assertTrue(userUtils.existUserId(1));
  }

  /**
   * 使用null的ID测试判断该ID对应的用户是否存在
   */
  @Test
  public void testExistUserIdWithNull() {
    assertFalse(userUtils.existUserId(null));
  }

  /**
   * 使用不存在用户的ID测试判断该ID对应的用户是否存在
   */
  @Test
  public void testExistUserIdIfNotExist() {
    assertFalse(userUtils.existUserId(-1));
  }

  /**
   * 使用存在用户的ID测试断言该ID对应的用户存在
   */
  @Test
  public void testCheckUserIdIsExistIfExist() {
    userUtils.assertExistUserId(2);
  }

  /**
   * 使用不存在用户的ID测试断言该ID对应的用户存在
   */
  @Test(expected = UserException.class)
  public void testCheckUserIdIsExistIfNotExist() {
    userUtils.assertExistUserId(100);
  }
}