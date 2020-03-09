package com.github.severinnitsche.utilities.convenience;

import com.github.severinnitsche.utilities.convenience.Exceptions.IlkNotFoundException;
import com.github.severinnitsche.utilities.convenience.Exceptions.MarkupNotSupportedException;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class IlkLoader {
  protected File location;
  protected BufferedReader reader;
  protected Map<Ilk,Class> ilkMap;
  protected Markup markupParser;
  
  public IlkLoader(String path, String markup) {
    location = new File(path);
    try {
      reader = new BufferedReader(new FileReader(location));
    } catch (FileNotFoundException e) {
      System.out.println("Warning: could not locate: "+path);
    }
    try {
      markupParser = Markup.MarkupForName(markup);
    } catch (MarkupNotSupportedException e) {
      System.out.println("Error: Markup failure");
    }
    ilkMap = new LinkedHashMap<>();
  }
  
  public IlkLoader(String path) {
    this(path,"yaml");
  }
  
  public IlkLoader() {
    this("res/utilities/convenience/ilks.yaml","yaml");
  }
  
  protected Ilk ilkForId(String id) throws IlkNotFoundException {
    for(Ilk ilk : ilkMap.keySet()) {
      if(ilk.getId().equals(id)) return ilk;
    }
    throw new IlkNotFoundException(id);
  }
  
  protected Ilk ilkForNameIdorAlias(String iid) throws IlkNotFoundException {
    for(Ilk ilk : ilkMap.keySet()) {
      if(ilk.getId().equals(iid)) return ilk;
      if(ilk.getName().equals(iid)) return ilk;
      for(String alias : ilk.getAlias()) {
        if(alias.equals(iid)) return ilk;
      }
    }
    throw new IlkNotFoundException(iid);
  }
  
  protected  Class<?> classForIlk(Ilk c) {
    return ilkMap.get(c);
  }
  
  public void load() throws IOException, ClassNotFoundException, IlkNotFoundException {
    StringBuilder contents = new StringBuilder();
    while(reader.ready()) {
      contents.append((char)reader.read());
    }
    Map<String,String> ilkSpecs = markupParser.parseMarkup(contents.toString());
    for(String id : ilkSpecs.keySet()) {
      registerIlk(id,ilkSpecs.get(id));
    }
  }
  
  protected void registerIlk(String id, String specs) throws ClassNotFoundException, IlkNotFoundException {
    Map<String,String> specValues = markupParser.parseMarkup(specs);
    Class clazz = Class.forName(specValues.get("class"));
    String name = specValues.get("name");
    String group = specValues.get("group");
    String[] alias = specValues.get("alias").split(";");
    Class<?>[] options = null;
    String[] optionNames = null;
    if(specValues.get("options") != "none") {
      Map<String, String> optionTypeMap = markupParser.parseMarkup(specValues.get("options"));
      options = new Class<?>[optionTypeMap.size()];
      optionNames = new String[optionTypeMap.size()];
      optionTypeMap.keySet().toArray(optionNames);
      int i = 0;
      for (String optionType : optionTypeMap.values()) {
        if (optionType.matches("\\[[a-zA-Z0-9]+\\]")) {
          optionType = optionType.replace("[", "").replace("]", "");
          switch (optionType) {
            case "double":
              options[i] = double[].class;
              break;
            case "float":
              options[i] = float[].class;
              break;
            case "int":
              options[i] = int[].class;
              break;
            case "byte":
              options[i] = byte[].class;
              break;
            case "boolean":
              options[i] = boolean[].class;
              break;
            case "char":
              options[i] = char[].class;
              break;
            case "long":
              options[i] = long[].class;
              break;
            case "String":
              options[i] = String[].class;
              break;
            default:
              options[i] = Class.forName("[L" + classForIlk(ilkForId(optionType)).getName() + ";");
          }
        } else {
          switch (optionType) {
            case "double":
              options[i] = double.class;
              break;
            case "float":
              options[i] = float.class;
              break;
            case "int":
              options[i] = int.class;
              break;
            case "byte":
              options[i] = byte.class;
              break;
            case "boolean":
              options[i] = boolean.class;
              break;
            case "char":
              options[i] = char.class;
              break;
            case "long":
              options[i] = long.class;
              break;
            case "String":
              options[i] = String.class;
              break;
            default:
              options[i] = classForIlk(ilkForId(optionType));
          }
        }
        i++;
      }
    }
    Ilk ilk = new Ilk(id,name,alias, options, optionNames, group);
    ilkMap.put(ilk,clazz);
  }
  
}
