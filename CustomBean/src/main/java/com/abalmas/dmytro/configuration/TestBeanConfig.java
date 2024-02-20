package com.abalmas.dmytro.configuration;

import com.abalmas.dmytro.bean.TestBeanPostProcessor;
import com.abalmas.dmytro.bean.TestBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.abalmas.dmytro")
public class TestBeanConfig {

  @Bean
  public TestBean testBean() {
    return new TestBean();
  }
}
