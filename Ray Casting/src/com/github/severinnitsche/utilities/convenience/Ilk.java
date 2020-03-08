package com.github.severinnitsche.utilities.convenience;

public final class Ilk {
  protected final String id;
  protected final String name;
  protected final String[] alias;
  protected final Class<?>[] options;
  protected final String[] optionNames;
  protected final String group;
  
  public Ilk(String id, String name, String[] alias, Class<?>[] options, String[] optionNames, String group) {
    this.id = id;
    this.name = name;
    this.alias = alias;
    this.options = options;
    this.optionNames = optionNames;
    this.group = group;
  }
  
  public Class<?>[] getOptions() {
    return options;
  }
  
  public String[] getOptionNames() {
    return optionNames;
  }
  
  public final String getId() {
    return id;
  }
  
  public final String getName() {
    return name;
  }
  
  public final String[] getAlias() {
    return alias;
  }
  
  public final int argLength() {
    return options.length;
  }
}
