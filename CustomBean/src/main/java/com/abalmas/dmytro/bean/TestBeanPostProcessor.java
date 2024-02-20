package com.abalmas.dmytro.bean;

import com.abalmas.dmytro.annotation.RandomValue;
import java.lang.reflect.Field;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class TestBeanPostProcessor implements BeanPostProcessor {

  @SneakyThrows
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    Field[] fields = bean.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (field.isAnnotationPresent(RandomValue.class)) {
        field.setAccessible(true);
        field.set(bean, Math.random());
      }
    }
    return bean;
  }
}


