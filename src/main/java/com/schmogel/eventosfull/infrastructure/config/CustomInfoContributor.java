package com.schmogel.eventosfull.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Component
public class CustomInfoContributor implements InfoContributor {

  private final String appVersion;
  @Value("${info.app.name}")
  private String appName;

  @Value("${info.app.description}")
  private String appDescription;

  @Value("${AMBIENTE:unknown}")
  private String ambiente;

  public CustomInfoContributor() throws IOException {
    Properties properties = new Properties();
    Resource resource = new ClassPathResource("version.properties");
    properties.load(resource.getInputStream());
    this.appVersion = properties.getProperty("version", "unknown");
  }

  @Override
  public void contribute(Info.Builder builder) {
    builder.withDetail("app", Map.of(
      "name", appName,
      "description", appDescription,
      "version", appVersion,
      "ambiente", ambiente
    ));
  }
}
