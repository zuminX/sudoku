package com.sudoku.framework.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

/**
 * JSON消息转换类
 *
 * @param <T> 类型
 */
public abstract class JSONMessageConvert<T> extends AbstractHttpMessageConverter<T> {

  public JSONMessageConvert() {
    super(MediaType.APPLICATION_JSON);
  }

  /**
   * 将JSON字符串转换为对象T
   *
   * @param json JSON字符串
   * @return 对象T
   */
  public abstract T jsonToT(String json) throws IOException;

  /**
   * 限定只应用在类T或其超类中
   *
   * @param clazz 测试支持的Class
   * @return 是否支持
   */
  @Override
  protected boolean supports(Class<?> clazz) {
    return getTClass().isAssignableFrom(clazz);
  }

  /**
   * 将输入的信息转为对象t
   *
   * @param clazz        返回对象的类型
   * @param inputMessage 要读取的HTTP输入消息
   * @return 转换后的对象
   * @throws IOException                     I/O异常
   * @throws HttpMessageNotReadableException 转换异常
   */
  @Override
  protected T readInternal(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
    String sourceStr = StreamUtils.copyToString(inputMessage.getBody(), StandardCharsets.UTF_8);
    return jsonToT(sourceStr);
  }

  /**
   * 将对象t以JSON形式进行输出
   *
   * @param t             对象
   * @param outputMessage 以JSON形式写入的HTTP输出消息
   * @throws IOException                     I/O异常
   * @throws HttpMessageNotWritableException 转换异常
   */
  @Override
  protected void writeInternal(T t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
    String str = new ObjectMapper().writeValueAsString(t);
    outputMessage.getBody().write(str.getBytes(StandardCharsets.UTF_8));
  }

  private Class<T> getTClass() {
    return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }
}
