package com.sudoku.framework.security.service.impl;

import static cn.hutool.core.date.DateUnit.MINUTE;
import static com.sudoku.common.constant.consist.RedisKeys.LOGIN_USER_PREFIX;
import static com.sudoku.common.constant.consist.RedisKeys.TOKEN_PREFIX;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.sudoku.framework.security.model.LoginUserBO;
import com.sudoku.framework.security.service.UserTokenService;
import com.sudoku.common.tools.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * 用户令牌业务层实现类
 */
@Data
@Service
@ConfigurationProperties("token")
public class UserTokenServiceImpl implements UserTokenService {

  /**
   * 令牌标识
   */
  private String header;
  /**
   * 令牌秘钥
   */
  private String secret;
  /**
   * 令牌有效期
   */
  private int expireTime;
  /**
   * 令牌刷新最大时间
   */
  private int refreshTime;

  @Autowired
  private RedisUtils redisUtils;

  /**
   * 获取登录用户
   *
   * @param request 请求对象
   * @return 登录用户对象
   */
  @Override
  public LoginUserBO getLoginUser(HttpServletRequest request) {
    String token = getToken(request);
    if (StrUtil.isNotBlank(token)) {
      Claims claims = paresToken(token);
      String uuid = (String) claims.get(LOGIN_USER_PREFIX);
      String userKey = getLoginTokenKey(uuid);
      return redisUtils.getObject(userKey);
    }
    return null;
  }

  /**
   * 创建Token
   *
   * @param loginUserBO 登录用户对象
   * @return 令牌
   */
  @Override
  public String createToken(LoginUserBO loginUserBO) {
    String uuid = UUID.fastUUID().toString();
    loginUserBO.setUuid(uuid);
    refreshToken(loginUserBO);
    return createToken(uuid);
  }

  /**
   * 刷新Token有效期
   *
   * @param loginUserBO 登录用户对象
   */
  @Override
  public void verifyToken(LoginUserBO loginUserBO) {
    long tokenExpireTime = loginUserBO.getExpireTime();
    if (tokenExpireTime - System.currentTimeMillis() <= refreshTime * MINUTE.getMillis()) {
      refreshToken(loginUserBO);
    }
  }

  /**
   * 删除登录用户信息
   *
   * @param uuid 唯一标识
   */
  @Override
  public void deleteLoginUser(String uuid) {
    if (StrUtil.isNotBlank(uuid)) {
      redisUtils.deleteObject(getLoginTokenKey(uuid));
    }
  }

  /**
   * 创建Token
   *
   * @param uuid 唯一标识
   * @return 令牌
   */
  private String createToken(String uuid) {
    return Jwts.builder().claim(LOGIN_USER_PREFIX, uuid).signWith(SignatureAlgorithm.HS512, secret).compact();
  }

  /**
   * 刷新Token
   *
   * @param loginUserBO 登录用户对象
   */
  private void refreshToken(LoginUserBO loginUserBO) {
    loginUserBO.setExpireTime(System.currentTimeMillis() + expireTime * MINUTE.getMillis());
    redisUtils.setObject(getLoginTokenKey(loginUserBO.getUuid()), loginUserBO, expireTime, TimeUnit.MINUTES);
  }

  /**
   * 解析Token
   *
   * @param token 令牌
   * @return 数据声明
   */
  private Claims paresToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  /**
   * 获取请求头中的Token
   *
   * @param request 请求对象
   * @return token
   */
  private String getToken(HttpServletRequest request) {
    String token = request.getHeader(this.header);
    return StrUtil.removePrefix(token, TOKEN_PREFIX);
  }

  /**
   * 获取登录Token的键值
   *
   * @param uuid 唯一标识
   * @return 键值
   */
  private String getLoginTokenKey(String uuid) {
    return LOGIN_USER_PREFIX + uuid;
  }
}
