package com.github.severinnitsche.utilities.logger;

import java.io.IOException;
import java.io.PrintStream;

public class Logger {
  
  protected PrintStream out;
  protected Format warning;
  protected Format error;
  protected Format load;
  
  public static final Logger LOGGER;
  
  static {
    Format warning = new Format(Format.Style.UNDERLINED,Format.Color.DARK_YELLOW,null);
    Format error = new Format(Format.Style.UNDERLINED,Format.Color.DARK_RED,null);
    Format load = new Format(Format.Style.NORMAL,Format.Color.LIGHT_CYAN,null);
    LOGGER = new Logger(System.out, warning, error, load);
  }
  
  public Logger(PrintStream out, Format warning, Format error, Format load) {
    this.out = out;
    this.warning = warning;
    this.error = error;
    this.load = load;
  }
  
  public static class Format {
    public Style style;
    public Color background;
    public Color color;
    
    public enum Style {
      BOLD,UNDERLINED,NORMAL
    }
  
    public enum Color {
      DAR_BLACK,DARK_RED,DARK_GREEN,DARK_YELLOW,DARK_BLUE,DARK_PURPLE,DARK_CYAN,DARK_WHITE,
      LIGHT_BLACK,LIGHT_RED,LIGHT_GREEN,LIGHT_YELLOW,LIGHT_BLUE,LIGHT_PURPLE,LIGHT_CYAN,LIGHT_WHITE;
    
      public static final String reset = "\033[0m";
    
      public final String COLOR;
      public final String BACKGROUND;
      public final String UNDERLINE;
      public final String BOLD;
    
      Color() {
        switch(this.toString()) {
          case "DAR_BLACK":
            COLOR = "\033[0;30m";
            BACKGROUND = "\033[40m";
            UNDERLINE = "\033[4;30m";
            BOLD = "\033[1;30";
            break;
          case "DARK_RED":
            COLOR = "\033[0;31m";
            BACKGROUND = "\033[41m";
            UNDERLINE = "\033[4;31m";
            BOLD = "\033[1;31";
            break;
          case "DARK_GREEN":
            COLOR = "\033[0;32m";
            BACKGROUND = "\033[42m";
            UNDERLINE = "\033[4;32m";
            BOLD = "\033[1;32";
            break;
          case "DARK_YELLOW":
            COLOR = "\033[0;33m";
            BACKGROUND = "\033[43m";
            UNDERLINE = "\033[4;33m";
            BOLD = "\033[1;33";
            break;
          case "DARK_BLUE":
            COLOR = "\033[0;34m";
            BACKGROUND = "\033[44m";
            UNDERLINE = "\033[4;34m";
            BOLD = "\033[1;34";
            break;
          case "DARK_PURPLE":
            COLOR = "\033[0;35m";
            BACKGROUND = "\033[45m";
            UNDERLINE = "\033[4;35m";
            BOLD = "\033[1;35";
            break;
          case "DARK_CYAN":
            COLOR = "\033[0;36m";
            BACKGROUND = "\033[46m";
            UNDERLINE = "\033[4;36m";
            BOLD = "\033[1;36";
            break;
          case "DARK_WHITE":
            COLOR = "\033[0;37m";
            BACKGROUND = "\033[47m";
            UNDERLINE = "\033[4;37m";
            BOLD = "\033[1;37";
            break;
          case "LIGHT_BLACK":
            COLOR = "\033[0;90m";
            BACKGROUND = "\033[0;100m";
            UNDERLINE = "\033[4;90m";
            BOLD = "\033[1;90";
            break;
          case "LIGHT_RED":
            COLOR = "\033[0;91";
            BACKGROUND = "\033[0;101m";
            UNDERLINE = "\033[4;91m";
            BOLD = "\033[1;91";
            break;
          case "LIGHT_GREEN":
            COLOR = "\033[0;92m";
            BACKGROUND = "\033[0;102m";
            UNDERLINE = "\033[4;92m";
            BOLD = "\033[1;92";
            break;
          case "LIGHT_YELLOW":
            COLOR = "\033[0;93m";
            BACKGROUND = "\033[0;103m";
            UNDERLINE = "\033[4;93m";
            BOLD = "\033[1;93";
            break;
          case "LIGHT_BLUE":
            COLOR = "\033[0;94m";
            BACKGROUND = "\033[0;104m";
            UNDERLINE = "\033[4;94m";
            BOLD = "\033[1;94";
            break;
          case "LIGHT_PURPLE":
            COLOR = "\033[0;95m";
            BACKGROUND = "\033[0;105m";
            UNDERLINE = "\033[4;95m";
            BOLD = "\033[1;95";
            break;
          case "LIGHT_CYAN":
            COLOR = "\033[0;96m";
            BACKGROUND = "\033[0;106m";
            UNDERLINE = "\033[4;96m";
            BOLD = "\033[1;96";
            break;
          case "LIGHT_WHITE":
            COLOR = "\033[0;97m";
            BACKGROUND = "\033[0;107m";
            UNDERLINE = "\033[4;97m";
            BOLD = "\033[1;97";
            break;
          default:
            COLOR = null;
            BACKGROUND = null;
            UNDERLINE = null;
            BOLD = null;
        }
      }
    }
    
    public Format(Style style, Color color, Color background) {
      this.style = style;
      this.color = color;
      this.background = background;
    }
    
    public String format(String msg) {
      if(System.getenv().get("TERM") == null && !System.getProperty("os.name").contains("Mac")) return msg;
      String prefix = "";
      switch (style) {
        case BOLD:
          prefix = color.BOLD;
          break;
        case UNDERLINED:
          prefix = color.UNDERLINE;
          break;
        case NORMAL:
          prefix = color.COLOR;
          break;
      }
      if(background != null) prefix += background.BACKGROUND;
      
      return prefix + msg + Color.reset;
    }
    
  }
  
  public void log(String msg, Format format, boolean newLine) {
    String formatted = format.format(msg);
    log(formatted,newLine);
  }
  
  public void log(String msg, boolean newLine) {
    String formatted = msg+(newLine?System.lineSeparator():"");
    out.print(formatted);
    out.flush();
  }
  
  public void log(String msg, Format format) {
    log(msg,format,true);
  }
  
  public void log(String msg) {
    log(msg,true);
  }
  
  public void error(String msg) {
    log("ERROR: "+msg,error);
  }
  
  public void warn(String msg) {
    log("WARNING: "+msg,warning);
  }
  
}
