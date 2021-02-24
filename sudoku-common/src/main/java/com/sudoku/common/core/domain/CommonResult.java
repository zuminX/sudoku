package com.sudoku.common.core.domain;

import com.sudoku.common.constant.enums.StatusCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("公共结果类")
public class CommonResult<T> implements Serializable {

  private static final long serialVersionUID = 8729965664919809866L;

  @ApiModelProperty("是否成功")
  private boolean success;

  @ApiModelProperty("状态码")
  private Integer code;

  @ApiModelProperty("http请求状态")
  private Integer httpStatus;

  @ApiModelProperty("消息")
  private String message;

  @ApiModelProperty("数据")
  private T data;

  /**
   * 请求出错
   *
   * @param statusCode 状态编码对象
   * @param <T>        数据类型
   * @return 经过包装的响应对象
   */
  public static <T> CommonResult<T> error(StatusCode statusCode) {
    CommonResult<T> result = new CommonResult<>();
    result.success = false;
    result.code = statusCode.getCode();
    result.httpStatus = statusCode.getStatus().value();
    result.message = statusCode.getMessage();
    return result;
  }

  /**
   * 请求出错
   *
   * @param statusCode 状态编码对象
   * @param <T>        数据类型
   * @param message    消息
   * @return 经过包装的响应对象
   */
  public static <T> CommonResult<T> error(StatusCode statusCode, String message) {
    CommonResult<T> result = error(statusCode);
    result.setMessage(message);
    return result;
  }

  /**
   * 请求成功
   *
   * @param <T>  数据类型
   * @return 经过包装的响应对象
   */
  public static <T> CommonResult<T> success() {
    CommonResult<T> result = new CommonResult<>();
    result.success = true;
    result.httpStatus = StatusCode.OK.getStatus().value();
    result.code = StatusCode.OK.getCode();
    return result;
  }

  /**
   * 请求成功
   *
   * @param data 数据
   * @param <T>  数据类型
   * @return 经过包装的响应对象
   */
  public static <T> CommonResult<T> success(T data) {
    CommonResult<T> result = new CommonResult<>();
    result.success = true;
    result.httpStatus = StatusCode.OK.getStatus().value();
    result.code = StatusCode.OK.getCode();
    result.data = data;
    return result;
  }

  /**
   * 请求成功
   *
   * @param data    数据
   * @param <T>     数据类型
   * @param message 消息
   * @return 经过包装的响应对象
   */
  public static <T> CommonResult<T> success(T data, String message) {
    CommonResult<T> result = success(data);
    result.setMessage(message);
    return result;
  }
}
