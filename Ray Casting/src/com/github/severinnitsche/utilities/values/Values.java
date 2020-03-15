package com.github.severinnitsche.utilities.values;

public class Values {
  
  public static final String LINE_RETURN;
  
  static {
    if(System.getProperty("os.name").contains("Windows")) {
      LINE_RETURN = "\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b";
    } else {
      LINE_RETURN = "\r";
    }
  }
  
}
