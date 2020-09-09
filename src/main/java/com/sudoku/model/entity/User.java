package com.sudoku.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

/**
 * 用户表对应的实体类
 */
@Data
@NoArgsConstructor
@TableName(value = "`user`")
public class User implements Serializable {

  private static final long serialVersionUID = -928831097063784277L;

  /**
   * 用户ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 用户名
   */
  @TableField(value = "username")
  private String username;

  /**
   * 密码
   */
  @TableField(value = "password")
  private String password;

  /**
   * 昵称
   */
  @TableField(value = "nickname")
  private String nickname;

  /**
   * 是否启用
   */
  @TableField(value = "enabled")
  private Boolean enabled;

  /**
   * 拥有的角色
   */
  private List<Role> roles;

  public User(String username, String password, String nickname, Boolean enabled) {
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.enabled = enabled;
  }
}