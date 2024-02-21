package com.abalmas.dmytro.bean;

import static com.mysql.cj.util.Util.stackTraceToString;

import com.abalmas.dmytro.annotation.RandomValue;
import java.lang.reflect.Field;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class TestBeanPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    if (bean instanceof TestBean) {
      Field[] fields = bean.getClass().getDeclaredFields();
      for (Field field : fields) {
        if (field.isAnnotationPresent(RandomValue.class)) {
          field.setAccessible(true);
          try {
            field.set(bean, Math.random());
          } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(stackTraceToString(e));
          }
        }
      }
    }
    return bean;
  }
}


