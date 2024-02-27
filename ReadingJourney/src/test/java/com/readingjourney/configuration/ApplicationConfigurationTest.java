package com.readingjourney.configuration;

import com.readingjourney.book.exception.GlobalExceptionHandler;
import javax.sql.DataSource;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@Configuration
public class ApplicationConfigurationTest {

  @Bean
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .build();
  }

  @Bean
  public LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
    validatorFactoryBean.setProviderClass(HibernateValidator.class);
    validatorFactoryBean.afterPropertiesSet();
    return validatorFactoryBean;
  }

  @Bean
  public GlobalExceptionHandler globalExceptionHandler() {
    return new GlobalExceptionHandler();
  }

}
