package com.sudoku.common.utils.file;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.sudoku.common.config.SudokuConfig;
import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.exception.FileException;
import com.sudoku.common.utils.DateUtils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件工具类
 */
public class FileUtils {

  public static String getAbsolutePath(String fileName) {
    return getAbsolutePath(SudokuConfig.getProfile(), fileName);
  }

  public static String getAbsolutePath(String baseDir, String fileName) {
    return StrUtil.isBlank(fileName) ? baseDir : baseDir + File.separator + fileName;
  }

  /**
   * 以默认配置进行文件上传
   *
   * @param file 上传的文件
   * @return 文件名称
   * @throws IOException
   */
  public static String upload(MultipartFile file) throws IOException {
    return upload(SudokuConfig.getProfile(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
  }

  /**
   * 根据文件路径上传
   *
   * @param baseDir 相对应用的基目录
   * @param file    上传的文件
   * @return 文件名称
   * @throws IOException
   */
  public static String upload(String baseDir, MultipartFile file) throws IOException {
    return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
  }

  /**
   * 文件上传
   *
   * @param baseDir          相对应用的基目录
   * @param file             上传的文件
   * @param allowedExtension 上传文件类型
   * @return 文件名
   * @throws IOException
   */
  public static String upload(String baseDir, MultipartFile file, String[] allowedExtension) throws IOException {
    assertAllowed(file, allowedExtension);

    String fileName = extractFilename(file);
    File desc = getAbsoluteFile(getAbsolutePath(baseDir, fileName));
    file.transferTo(desc);
    return fileName;
  }

  /**
   * 编码文件名
   */
  public static String extractFilename(MultipartFile file) {
    String extension = getExtension(file);
    return DateUtils.plainDateStr() + "/" + UUID.fastUUID() + "." + extension;
  }

  private static File getAbsoluteFile(String path) throws IOException {
    File desc = new File(path);
    if (!desc.getParentFile().exists()) {
      desc.getParentFile().mkdirs();
    }
//    if (!desc.exists()) {
//      desc.createNewFile();
//    }
    return desc;
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
