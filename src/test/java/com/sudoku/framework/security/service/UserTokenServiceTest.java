package com.sudoku.framework.security.service;

import mock.RedisUtilsMock;
import org.junit.Before;
import org.junit.Test;

/**
 * 用户令牌业务层实现类的测试类
 */
public class UserTokenServiceTest {

  private UserTokenService userTokenService;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() {
    userTokenService = new UserTokenService(new RedisUtilsMock());
  }

  @Test
  public void getLoginUser() {
  }

  @Test
  public void createToken() {
  }

  @Test
  public void verifyToken() {
  }

  @Test
  public void deleteLoginUser() {
  }
}