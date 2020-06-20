package com.sudoku.log;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Aspect
@Component
@Slf4j
public class LogAspect {

  private static final String BASIC_LOG_TEMPLATE = "[{}]";
  private static final String TIME_LOG_TEMPLATE = "[共计用时: {} ms]";

  /**
   * 配置织入点 处理所有带有Log注解的方法
   */
  @Pointcut("@annotation(com.sudoku.log.Log)")
  public void logPointCut() {
  }

  @Around("logPointCut()")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    long startTime = System.currentTimeMillis();
    try {
      Object result = point.proceed();
      handleLog(point, startTime, null);
      return result;
    } catch (Throwable throwable) {
      handleLog(point, startTime, throwable);
      throw throwable;
    }
  }

  private void handleLog(JoinPoint joinPoint, long startTime, Throwable throwable) {
    Log log = getAnnotationLog(joinPoint);
    if (log == null) {
      return;
    }
    long spendTime = System.currentTimeMillis() - startTime;
    String logContent = buildLogContent(log, joinPoint.getArgs(), spendTime, throwable);
    if (throwable != null) {
      recordSuccessLog(logContent, spendTime);
    } else {
      recordErrorLog(logContent);
    }
  }

  private void recordSuccessLog(String logContent, long spendTime) {
    if (spendTime < 1000) {
      log.info(logContent);
      return;
    }
    if (spendTime < 5000) {
      log.warn(logContent);
      return;
    }
    log.error(logContent);
  }

  private void recordErrorLog(String logContent) {
    log.error(logContent);
  }

  private String buildLogContent(Log log, Object[] paramsArray, long spendTime, Throwable throwable) {
    StringBuilder result = new StringBuilder();
    result.append(toLogString(log.value()));
    result.append(toLogString(log.businessType()));
    result.append(StrUtil.format(TIME_LOG_TEMPLATE, spendTime));
    if (log.isSaveParameterData()) {
      result.append(StrUtil.sub(paramDataToLogString(paramsArray), 0, 1000));
    }
    if (throwable != null) {
      result.append(Arrays.toString(throwable.getStackTrace()));
    }
    return result.toString();
  }

  /**
   * 拼接参数数据
   */
  private String paramDataToLogString(Object[] paramsData) {
    String result = "";
    if (ArrayUtil.isNotEmpty(paramsData)) {
      result = Arrays.stream(paramsData).filter(this::notFilterObject).map(this::toLogString).collect(Collectors.joining());
    }
    return result;
  }

  private String toLogString(Object data) {
    return StrUtil.format(BASIC_LOG_TEMPLATE, data);
  }

  /**
   * 判断是否不是过滤该对象
   *
   * @param o 对象
   * @return 需要过滤返回false，否则返回true
   */
  private boolean notFilterObject(Object o) {
    return !(o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse);
  }

  /**
   * 获取日志注解 如果存在就获取
   */
  private Log getAnnotationLog(JoinPoint joinPoint) {
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method method = methodSignature.getMethod();
    return method != null ? method.getAnnotation(Log.class) : null;
  }
}
