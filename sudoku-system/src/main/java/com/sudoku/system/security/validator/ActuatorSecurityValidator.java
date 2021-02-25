package com.sudoku.system.security.validator;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ActuatorSecurityValidator {

  @Value("${management.accessToken}")
  private String accessToken;

  private static final String HEADER_NAME = "A-AccessToken";

  public boolean check(HttpServletRequest request) {
    String header = request.getHeader(HEADER_NAME);
    return accessToken.equals(header);
  }

}