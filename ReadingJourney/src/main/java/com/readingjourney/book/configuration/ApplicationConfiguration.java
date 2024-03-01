package com.readingjourney.book.configuration;

import jakarta.persistence.EntityManagerFactory;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The type Application configuration.
 */
@Configuration
@EnableWebMvc
@ComponentScan(value = {"com.readingjourney.book", "com.readingjourney.account"})
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = {"com.readingjourney.book.repository",
    "com.readingjourney.account.repository"})
@EnableTransactionManagement
public class ApplicationConfiguration {

  private final Environment environment;

  public ApplicationConfiguration(Environment environment) {
    this.environment = environment;
  }

  private Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.dialect",
        environment.getRequiredProperty("hibernate.dialect"));
    properties.put("hibernate.show_sql",
        environment.getRequiredProperty("hibernate.show_sql"));
    properties.put("hibernate.default_schema",
        environment.getRequiredProperty("hibernate.default_schema"));
    properties.put("hibernate.hbm2ddl.auto",
        environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
    return properties;
  }

  /**
   * Creates and configures a DataSource bean for the application, using the properties defined in
   * the environment.
   *
   * @return the configured DataSource bean
   */
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
    dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
    dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
    dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
    return dataSource;
  }

  /**
   * A function that returns a new HibernateJpaVendorAdapter instance.
   *
   * @return a new HibernateJpaVendorAdapter instance
   */
  public HibernateJpaVendorAdapter vendorAdapter() {
    return new HibernateJpaVendorAdapter();
  }

  /**
   * Creates and configures a LocalContainerEntityManagerFactoryBean for the application, using the
   * configured DataSource, entity package to scan, JPA vendor adapter, and Hibernate properties.
   *
   * @return the configured LocalContainerEntityManagerFactoryBean
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean localContainerEntity =
        new LocalContainerEntityManagerFactoryBean();
    localContainerEntity.setDataSource(dataSource());
    localContainerEntity.setPackagesToScan("com.readingjourney.book.entity",
        "com.readingjourney.account.entity");
    localContainerEntity.setJpaVendorAdapter(vendorAdapter());
    localContainerEntity.setJpaProperties(hibernateProperties());
    return localContainerEntity;
  }

  /**
   * Creates and configures a JpaTransactionManager bean for managing JPA transactions using the
   * specified EntityManagerFactory.
   *
   * @param entityManagerFactory the EntityManagerFactory used for the transactions
   * @return the configured JpaTransactionManager bean
   */
  @Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }
}
