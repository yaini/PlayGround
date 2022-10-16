package com.yaini.converter;

import com.yaini.entity.SimpleEntity;
import com.yaini.model.Simple;

public class SimpleEntityConverter {

  public static Simple to(SimpleEntity entity) {
    return Simple.builder().id(entity.getId()).name(entity.getName()).build();
  }
}
