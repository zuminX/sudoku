package com.sudoku.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 路由控制类
 */
@RestController
@Api(tags = "路由API接口")
public class RouterController {

  @GetMapping("/")
  @ApiOperation("访问首页的路由")
  public ModelAndView index() {
    return new ModelAndView("index");
  }
}
