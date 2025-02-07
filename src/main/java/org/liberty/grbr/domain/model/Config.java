package org.liberty.grbr.domain.model;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Config {

  private final String username;
  private final String token;
  private final List<String> ignoreList;

  public Config(Properties properties) {
    username = properties.getProperty("org.liberty.grbr.username", "");
    token = properties.getProperty("org.liberty.grbr.auth.token", "");
    ignoreList =
        Arrays.stream(properties.getProperty("org.liberty.grbr.ignore.list", "").split(","))
            .map(String::trim)
            .filter(e -> !e.isEmpty())
            .toList();
  }

  public String getUsername() {
    return username;
  }

  public String getToken() {
    return token;
  }

  public List<String> getIgnoreList() {
    return ignoreList;
  }
}
