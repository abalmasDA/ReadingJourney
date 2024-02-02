package com.abalmas.dmytro.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(value = "com.abalmas.dmytro")
@EnableWebMvc
public class AppConfig {



}
