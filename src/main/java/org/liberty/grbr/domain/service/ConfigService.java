package org.liberty.grbr.domain.service;

import java.io.IOException;
import java.util.Properties;
import org.liberty.grbr.application.exception.ConfigException;
import org.liberty.grbr.domain.model.Config;

public class ConfigService {

  private static Config config;

  public ConfigService() {
    createConfig();
  }

  private void createConfig() {
    try {
      Properties properties = new Properties(2);
      properties.load(ConfigService.class.getResourceAsStream("/app.properties"));
      config = new Config(properties);
    } catch (IOException e) {
      throw new ConfigException(e);
    }
  }

  public Config fetchConfig() {
    if (config == null) {
      createConfig();
    }

    return config;
  }
}
