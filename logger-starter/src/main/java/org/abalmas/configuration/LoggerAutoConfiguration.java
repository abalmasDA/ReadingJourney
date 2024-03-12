package org.abalmas.configuration;

import lombok.extern.slf4j.Slf4j;
import org.abalmas.aspect.LoggableAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(LoggerProperties.class)
@ConditionalOnProperty(prefix = "org.abalmas", name = "enabled", havingValue = "true")
public class LoggerAutoConfiguration {
  @Bean
  public LoggableAspect loggableAspect() {
    return new LoggableAspect();
  }
}
