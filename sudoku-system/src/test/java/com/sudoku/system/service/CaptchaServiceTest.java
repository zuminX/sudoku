package com.sudoku.system.service;

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
public class CaptchaServiceTest {

  private RedisUtils redisUtils;

  private CaptchaService captchaService;

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

    captchaService = spy(new CaptchaService(redisUtils));
    when(captchaService.getCaptchaKey(anyString())).thenAnswer(answer -> answer.getArgument(0));
  }

  /**
   * 测试检查验证码是否正确
   */
  @Test
  public void testCheckCaptcha() {
    captchaService.checkCaptcha(uuid, code);
    verifyDeleteCaptchaInRedis();
  }

  /**
   * 使用小写字母测试检查验证码是否正确
   */
  @Test
  public void testCheckCaptchaWithLowerCaseCode() {
    captchaService.checkCaptcha(uuid, code.toLowerCase());
    verifyDeleteCaptchaInRedis();
  }

  /**
   * 使用大写字母测试检查验证码是否正确
   */
  @Test
  public void testCheckCaptchaWithUpperCaseCode() {
    captchaService.checkCaptcha(uuid, code.toUpperCase());
    verifyDeleteCaptchaInRedis();
  }

  /**
   * 使用不存在验证码的UUID测试检查验证码是否正确
   */
  @Test(expected = CaptchaException.class)
  public void testCheckCaptchaWithErrorUUID() {
    captchaService.checkCaptcha("NotFound", code);
  }

  /**
   * 使用错误的验证码测试检查验证码是否正确
   */
  @Test(expected = CaptchaException.class)
  public void testCheckCaptchaWithErrorCode() {
    captchaService.checkCaptcha(uuid, "1213");
  }

  /**
   * 验证在Redis中移除该验证码的数据
   */
  private void verifyDeleteCaptchaInRedis() {
    assertNull(redisUtils.getObject(uuid));
  }
}