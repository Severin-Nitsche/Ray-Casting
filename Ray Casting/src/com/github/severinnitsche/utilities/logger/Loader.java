package com.github.severinnitsche.utilities.logger;

import java.text.DecimalFormat;

public class Loader{
  
  protected Logger logger;
  protected Logger.Format format;
  
  public Loader(Logger logger, Logger.Format format) {
    this.format = format;
    this.logger = logger;
  }
  
  public Loader() {
    this(Logger.LOGGER,new Logger.Format(Logger.Format.Style.NORMAL, Logger.Format.Color.LIGHT_CYAN,null));
  }
  
  public void load(int n, int d) {
    double p = n/(double)d;
    StringBuilder builder = new StringBuilder();
    builder.append("\r");
    DecimalFormat f = new DecimalFormat("#.##");
    String percent = "["+n+"/"+d+" | "+f.format(p*100)+"%]";
    int tokens = 80-2-percent.length();
    builder.append(percent);
    builder.append("[");
    for(int i=0; i<tokens; i++) {
      if(i<tokens*p) {
        builder.append("=");
      } else {
        builder.append(" ");
      }
    }
    builder.append("]");
    logger.log(builder.toString(),format,false);
  }
  
}
