package com.sudoku.framework.convert;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sudoku.common.constant.enums.StatisticsDate;
import com.sudoku.common.core.domain.StatisticsDateRange;
import java.time.LocalDate;

/**
 * 统计日期范围消息转换类
 */
public class StatisticsDateRangeMessageConvert extends JSONMessageConvert<StatisticsDateRange> {

  /**
   * 将JSON字符串转换为对象T
   *
   * @param json JSON字符串
   * @return 对象T
   */
  @Override
  public StatisticsDateRange jsonToT(String json) throws JsonProcessingException {
    if (StrUtil.isBlank(json)) {
      return null;
    }
    ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
    return new StatisticsDateRange(toLocalDate(node, "startDate"), toLocalDate(node, "endDate"), toStatisticsDate(node));
  }

  private LocalDate toLocalDate(ObjectNode node, String filedName) {
    JsonNode jsonNode = node.get(filedName);
    return jsonNode == null ? null : LocalDate.parse(jsonNode.textValue());
  }

  private StatisticsDate toStatisticsDate(ObjectNode node) {
    JsonNode jsonNode = node.get("statisticsDate");
    return jsonNode == null ? null : StatisticsDate.findByName(jsonNode.textValue());
  }
}
