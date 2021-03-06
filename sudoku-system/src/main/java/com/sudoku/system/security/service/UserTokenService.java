package com.sudoku.system.security.service;

import static cn.hutool.core.date.DateUnit.MINUTE;
import static com.sudoku.common.constant.consist.RedisKeys.LOGIN_USER_PREFIX;
import static com.sudoku.common.constant.consist.RedisKeys.TOKEN_PREFIX;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.sudoku.common.tools.RedisUtils;
import com.sudoku.system.model.bo.LoginUserBO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * 用户令牌业务层类
 */
@Setter
@Service
@ConfigurationProperties("token")
public class UserTokenService {

  private final RedisUtils redisUtils;

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

  public UserTokenService(RedisUtils redisUtils) {
    this.redisUtils = redisUtils;
  }

  /**
   * 设置用户身份信息
   */
  public void setLoginUser(LoginUserBO loginUser) {
    if (loginUser != null && StrUtil.isNotBlank(loginUser.getUuid())) {
      refreshToken(loginUser);
    }
  }

  /**
   * 获取登录用户
   *
   * @param request 请求对象
   * @return 登录用户对象
   */
  public LoginUserBO getLoginUser(HttpServletRequest request) {
    String token = getToken(request);
    if (StrUtil.isBlank(token)) {
      return null;
    }
    Claims claims = paresToken(token);
    String uuid = (String) claims.get(LOGIN_USER_PREFIX);
    String userKey = getLoginTokenKey(uuid);
    return redisUtils.getObject(userKey);
  }

  /**
   * 创建Token
   *
   * @param loginUserBO 登录用户对象
   * @return 令牌
   */
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
