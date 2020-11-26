package com.sudoku.framework.security.service.impl;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.sudoku.common.exception.CaptchaException;
import com.sudoku.common.tools.RedisUtils;
import java.io.IOException;
import mock.RedisUtilsMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * 验证码业务层实现类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class CaptchaServiceImplTest {

  private RedisUtils redisUtils;

  private CaptchaServiceImpl captchaService;

  private String uuid;

  private String code;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() throws IOException {
    uuid = "TESTUUID";
    code = "1We1";

    redisUtils = new RedisUtilsMock();
    redisUtils.setObject(uuid, code);

    captchaService = spy(new CaptchaServiceImpl(redisUtils));
    when(captchaService.getCaptchaKey(anyString())).thenAnswer(answer -> answer.getArgument(0));
  }

  /**
   * 测试检查验证码是否正确
   */
  @Test
  public void testCheckCaptcha() {
    captchaService.checkCaptcha(uuid, code);
    assertDeleteCaptchaInRedis();
  }

  /**
   * 测试检查小写的验证码是否正确
   */
  @Test
  public void testCheckCaptchaWithLowerCaseCode() {
    captchaService.checkCaptcha(uuid, code.toLowerCase());
    assertDeleteCaptchaInRedis();
  }

  /**
   * 测试检查大写的验证码是否正确
   */
  @Test
  public void testCheckCaptchaWithUpperCaseCode() {
    captchaService.checkCaptcha(uuid, code.toUpperCase());
    assertDeleteCaptchaInRedis();
  }

  /**
   * 测试使用不存在验证码的UUID来检查验证码是否正确
   */
  @Test(expected = CaptchaException.class)
  public void testCheckCaptchaWithErrorUUID() {
    captchaService.checkCaptcha("NotFound", code);
  }

  /**
   * 测试使用错误的验证码来检查验证码是否正确
   */
  @Test(expected = CaptchaException.class)
  public void testCheckCaptchaWithErrorCode() {
    captchaService.checkCaptcha(uuid, "1213");
  }

  /**
   * 断言在Redis中移除该验证码的数据
   */
  private void assertDeleteCaptchaInRedis() {
    assertNull(redisUtils.getObject(uuid));
  }
}