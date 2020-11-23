package com.sudoku.common.utils.project;

import com.sudoku.common.exception.UserException;
import com.sudoku.project.mapper.UserMapper;
import com.sudoku.project.model.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    Mockito.when(userMapper.selectById(Mockito.anyInt())).thenAnswer(answer -> {
      Integer id = answer.getArgument(0);
      if (!idList.contains(id)) {
        return null;
      }
      User user = new User();
      user.setId(id);
      return user;
    });
  }

  /**
   * 若指定ID的用户存在，应返回true
   */
  @Test
  public void testExistUserIdIfExist() {
    Assert.assertTrue(userUtils.existUserId(1));
  }

  /**
   * 若查找ID为null的用户，应返回false
   */
  @Test
  public void testExistUserIdWithNull() {
    Assert.assertFalse(userUtils.existUserId(null));
  }

  /**
   * 若指定ID的用户不存在，应返回false
   */
  @Test
  public void testExistUserIdIfNotExist() {
    Assert.assertFalse(userUtils.existUserId(-1));
  }

  /**
   * 若指定ID的用户存在，不应抛出异常
   */
  @Test
  public void testCheckUserIdIsExistIfExist() {
    userUtils.checkUserIdExist(2);
  }

  /**
   * 若指定ID的用户不存在，应抛出UserException异常
   */
  @Test(expected = UserException.class)
  public void testCheckUserIdIsExistIfNotExist() {
    userUtils.checkUserIdExist(100);
  }
}