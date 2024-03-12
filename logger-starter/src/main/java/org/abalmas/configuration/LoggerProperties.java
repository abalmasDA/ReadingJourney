package org.abalmas.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "org.abalmas")
public class LoggerProperties {
  private boolean enabled;

}
