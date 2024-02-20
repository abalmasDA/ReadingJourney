package com.abalmas.dmytro.bean;

import com.abalmas.dmytro.annotation.RandomValue;
import lombok.Getter;

@Getter
public class TestBean {

  @RandomValue
  private double value;

}
