package com.readingjourney.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class TestDispatcherServlet extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return null;
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[]{ApplicationConfigurationTest.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[]{"/"};
  }
}
