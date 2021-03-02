package com.sudoku.common.tools;

import cn.hutool.core.convert.Convert;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 客户端工具类
 */
public class ServletUtils {

  /**
   * 私有构造方法，防止实例化
   */
  private ServletUtils() {
  }

  /**
   * 将数据转换为JSON数据响应给客户端
   *
   * @param response HttpServlet响应对象
   * @param data     数据
   * @throws IOException IO异常
   */
  public static void returnJsonData(HttpServletResponse response, Object data) throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");
    PrintWriter writer = response.getWriter();
    writer.write(new ObjectMapper().writeValueAsString(data));
    writer.flush();
    writer.close();
  }

  /**
   * 获取请求对象
   *
   * @return 请求对象
   */
  public static HttpServletRequest getRequest() {
    return getRequestAttributes().getRequest();
  }

  /**
   * 获取响应对象
   *
   * @return 响应对象
   */
  public static HttpServletResponse getResponse() {
    return getRequestAttributes().getResponse();
  }

  /**
   * 获取String参数
   *
   * @param name 参数名称
   * @return 参数
   */
  public static String getParameter(String name) {
    return getRequest().getParameter(name);
  }

  /**
   * 获取String参数
   *
   * @param name         参数名称
   * @param defaultValue 默认值
   * @return 参数
   */
  public static String getParameter(String name, String defaultValue) {
    return Convert.toStr(getRequest().getParameter(name), defaultValue);
  }

  /**
   * 获取Integer参数
   *
   * @param name 参数名称
   * @return 参数
   */
  public static Integer getParameterToInt(String name) {
    return Convert.toInt(getRequest().getParameter(name));
  }

  /**
   * 获取Integer参数
   *
   * @param name         参数名称
   * @param defaultValue 默认值
   * @return 参数
   */
  public static Integer getParameterToInt(String name, Integer defaultValue) {
    return Convert.toInt(getRequest().getParameter(name), defaultValue);
  }

  /**
   * 获取请求属性
   *
   * @return Servlet请求属性
   */
  private static ServletRequestAttributes getRequestAttributes() {
    RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
    return (ServletRequestAttributes) attributes;
  }
}
