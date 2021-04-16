package com.sudoku.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * oss上传文件的回调结果
 */
@Data
public class OOSCallbackDTO {

  @ApiModelProperty("文件地址")
  private String filePath;
  @ApiModelProperty("文件大小")
  private String size;
  @ApiModelProperty("文件的mimeType")
  private String mimeType;
}
