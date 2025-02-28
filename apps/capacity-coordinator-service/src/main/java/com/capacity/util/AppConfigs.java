package com.capacity.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "properties")
public class AppConfigs {

  private Map<String, String> general;
  private Map<String, String> schemas;
  private Map<String, String> environment;
  private Map<String, String> amqp;

}
