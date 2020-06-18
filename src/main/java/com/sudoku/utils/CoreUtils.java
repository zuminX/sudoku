package com.sudoku.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudoku.constant.consist.SettingParameter;
import com.sudoku.model.entity.User;
import com.sudoku.model.vo.RankDataVO.RankItemVO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 业务相关工具类
 */
public class CoreUtils {

  /**
   * 将数据绑定session中
   *
   * @param session session对象
   * @param key     该数据绑定的名称
   * @param value   该数据对象
   */
  public static void setSessionAttribute(HttpSession session, String key, Object value) {
    //移除之前可能存在的数据
    session.removeAttribute(key);
    //绑定数据到session中
    session.setAttribute(key, value);
  }

  /**
   * 响应回客户端
   *
   * @param response 响应对象
   * @param data     数据
   */
  public static void responseClient(ServletResponse response, Object data) throws IOException {
    //设置编码
    response.setContentType("application/json;charset=utf-8");
    PrintWriter out = response.getWriter();

    //返回消息
    String s = new ObjectMapper().writeValueAsString(data);
    out.write(s);
    out.flush();
    out.close();
  }

  /**
   * 跳转到首页
   *
   * @param request  请求对象
   * @param response 响应对象
   * @throws ServletException Servlet异常
   * @throws IOException      I/O异常
   */
  public static void returnHome(ServletRequest request, ServletResponse response) throws ServletException, IOException {
    request.getRequestDispatcher("/").forward(request, response);
  }

  /**
   * 获取密码编码器
   *
   * @return BCrypt密码编码器
   */
  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 获取当前登录的用户
   *
   * @return 用户对象
   */
  public static User getNowUser() {
    return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
  }

  /**
   * 填充排行项列表到设定的数量
   *
   * @param <T> 数据的类型
   * @return 填充后的排行项列表
   */
  public static <T> ArrayList<RankItemVO<T>> fillUpRankItemList(ArrayList<RankItemVO<T>> rankItemVOArrayList) {
    for (int i = rankItemVOArrayList.size(); i < SettingParameter.RANKING_NUMBER; i++) {
      rankItemVOArrayList.add(new RankItemVO<>(null, null));
    }
    return rankItemVOArrayList;
  }

}
