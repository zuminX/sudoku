package com.sudoku.common.utils.file;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.exception.FileException;
import com.sudoku.common.template.OSSTemplate;
import com.sudoku.common.utils.DateUtils;
import java.io.IOException;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件工具类
 */
@Component
public class FileUtils {

  public static String getAbsolutePath(String dir, String fileName) {
    return StrUtil.isBlank(fileName) ? dir : dir + '/' + fileName;
  }

  /**
   * 编码文件名
   */
  public static String extractFilename(MultipartFile file) {
    String extension = getExtension(file);
    return DateUtils.plainDateStr() + "/" + UUID.fastUUID() + "." + extension;
  }

  /**
   * 文件格式校验
   *
   * @param file 上传的文件
   */
  public static void assertAllowed(MultipartFile file, String[] allowedExtension) {
    String extension = getExtension(file);
    if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
      if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
        throw new FileException(StatusCode.FILE_EXPECT_IMAGE);
      } else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
        throw new FileException(StatusCode.FILE_EXPECT_MEDIA);
      } else {
        throw new FileException(StatusCode.FILE_TYPE_ILLEGAL);
      }
    }
  }

  /**
   * 判断MIME类型是否是允许的MIME类型
   *
   * @param extension
   * @param allowedExtension
   * @return
   */
  public static boolean isAllowedExtension(String extension, String[] allowedExtension) {
    return Arrays.stream(allowedExtension).anyMatch(str -> str.equalsIgnoreCase(extension));
  }

  /**
   * 获取文件名的后缀
   *
   * @param file 表单文件
   * @return 后缀名
   */
  public static String getExtension(MultipartFile file) {
    String extension = FileNameUtil.getSuffix(file.getOriginalFilename());
    return StrUtil.isBlank(extension) ? MimeTypeUtils.getExtension(file.getContentType()) : extension;
  }

}
