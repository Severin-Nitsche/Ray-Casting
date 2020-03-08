package com.github.severinnitsche.utilities.convenience;

import com.github.severinnitsche.utilities.convenience.Exceptions.MarkupNotSupportedException;

import java.util.LinkedHashMap;
import java.util.Map;

public class Markup {
  private static Map<String,Markup> markups;
  
  static {
    markups = new LinkedHashMap<>();
    markups.put("yaml",new Markup());
  }
  
  private Markup() {
  
  }
  
  public static void registerMarkup(String markup, Markup clazz) {
    markups.put(markup,clazz);
  }
  
  public static Markup MarkupForName(String markup) throws MarkupNotSupportedException {
    for(String markuq : markups.keySet()) {
      if(markuq.equals(markup)) return markups.get(markup);
    }
    throw new MarkupNotSupportedException(markup);
  }
  
  protected static final boolean in(String s, String[] v) {
    for(String k : v) {
      if(s.equals(k)) return true;
    }
    return false;
  }
  
  public Map<String,String> parseMarkup(String markup) {
    Map<String,String> map = new LinkedHashMap<>();
    
    markup = markup.trim();
    String[] lines = markup.split("\n");
    for(int i=0; i<lines.length; i++) {
      String line = lines[i];
      if(line.matches("[a-zA-Z0-9]+:.*")) {
        if(!line.trim().endsWith(":")) {
          //value is in concurrent line
          String[] keyValue = line.split(":");
          String key = keyValue[0].trim();
          String value = keyValue[1].trim();
          value = value.replaceAll("\"","");
          map.put(key,value);
        } else {
          //value is in the following lines
          String key = line.trim().replace(":","");
          
          //replaces everything but the first white Space
          String indent = "  ";//lines[i+1].replaceFirst(lines[i+1].replaceFirst("\\s",""),"");
          
          StringBuilder value = new StringBuilder();
          for(i=i+1; lines[i].startsWith(indent); i++) {
            value.append(lines[i].replaceFirst(indent, "")).append('\n');
            if(i+1==lines.length) break;
          }
          
          map.put(key,value.toString());
          
        }
      }
    }
    return map;
  }
  
}
