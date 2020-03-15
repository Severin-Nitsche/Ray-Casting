package com.github.severinnitsche.utilities.values;

public class Values {
  
  public static final String LINE_RETURN;
  public static final int WIDTH;
  
  static {
    if(System.getenv().get("TERM") == null && !System.getProperty("os.name").contains("Mac")) {
      WIDTH = 79;
      LINE_RETURN = "\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b";
    } else {
      WIDTH = 80;
      LINE_RETURN = "\r";
    }
  }
  
}
