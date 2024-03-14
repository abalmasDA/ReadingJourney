//package com.abalmas.dmytro;
//
//import com.abalmas.dmytro.bean.TestBean;
//import com.abalmas.dmytro.configuration.TestBeanConfig;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//public class Main {
//
//  public static void main(String[] args) {
//    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
//        TestBeanConfig.class)) {
//      TestBean testBean = context.getBean(TestBean.class);
//      System.out.println(testBean.getValue());
//    }
//
//  }
//
//}
