package com.sudoku.framework.convert;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sudoku.common.constant.enums.StatisticsDate;
import com.sudoku.common.core.domain.LocalDateRange;
import com.sudoku.common.core.domain.StatisticsDateRange;

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
    return new StatisticsDateRange(toLocalDateRange(node), toStatisticsDate(node));
  }

  private LocalDateRange toLocalDateRange(ObjectNode node) {
    JsonNode jsonNode = node.get("dateRange");
    if (jsonNode == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    return mapper.convertValue(jsonNode, LocalDateRange.class);
  }

  private StatisticsDate toStatisticsDate(ObjectNode node) {
    JsonNode jsonNode = node.get("statisticsDate");
    return jsonNode == null ? null : StatisticsDate.findByName(jsonNode.textValue());
  }
}
