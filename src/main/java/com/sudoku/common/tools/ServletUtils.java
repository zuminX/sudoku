package com.sudoku.common.tools;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
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
   * 获取请求对象
   *
   * @return 请求对象
   */
  public static HttpServletRequest getRequest() {
    return getRequestAttributes().getRequest();
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
