package com.github.severinnitsche.utilities.convenience;

import com.github.severinnitsche.essentials.implemented.Sphere;
import com.github.severinnitsche.essentials.meta.Viewer;
import com.github.severinnitsche.utilities.convenience.Exceptions.IlkNotFoundException;
import com.github.severinnitsche.utilities.convenience.Exceptions.MarkupNotSupportedException;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.visual.Color;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SceneParser {
  protected IlkLoader loader;
  protected Markup markup;
  protected File location;
  protected BufferedReader reader;
  Map<String,Object> objectMap;
  
  {
    loader = new IlkLoader();
    try {
      loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (IlkNotFoundException e) {
      e.printStackTrace();
    }
    try {
      markup = Markup.MarkupForName("yaml");
    } catch (MarkupNotSupportedException e) {
      e.printStackTrace();
    }
    objectMap = new LinkedHashMap<>();
  }
  
  public SceneParser() throws FileNotFoundException {
    File defaultDir = new File("res/scenes");
    for(File file : defaultDir.listFiles()) {
      if(file.getName().endsWith(".yaml")) {
        location = file;
        break;
      }
    }
    if(location==null) throw new FileNotFoundException("Could not locate yaml file in default directory");
    reader = new BufferedReader(new FileReader(location));
  }
  
  public void load() throws IOException, IlkNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    StringBuilder contents = new StringBuilder();
    while(reader.ready()) {
      contents.append((char)reader.read());
    }
    Map<String,String> nameSpecMap = markup.parseMarkup(contents.toString());
    for(String name : nameSpecMap.keySet()) {
      Map<String,String> specValue = markup.parseMarkup(nameSpecMap.get(name));
      Ilk ilk = loader.ilkForNameIdorAlias(specValue.get("ilk"));
      Object[] args = new Object[ilk.argLength()];
      Map<String,String> argValue = markup.parseMarkup(specValue.get("arguments"));
      for(int i=0; i<ilk.argLength(); i++) {
        String value = argValue.get(ilk.getOptionNames()[i]);
        if(ilk.getOptions()[i]==double.class) {
          args[i] = Double.parseDouble(value);
        } else if(ilk.getOptions()[i]==float.class) {
          args[i] = Float.parseFloat(value);
        } else if(ilk.getOptions()[i]==int.class) {
          args[i] = Integer.parseInt(value);
        } else if(ilk.getOptions()[i]==long.class) {
          args[i] = Long.parseLong(value);
        } else if(ilk.getOptions()[i]==boolean.class) {
          args[i] = Boolean.parseBoolean(value);
        } else if(ilk.getOptions()[i]==char.class) {
          args[i] = value.charAt(0);
        } else if(ilk.getOptions()[i]==byte.class) {
          args[i] = (byte)Integer.parseInt(value);
        } else if(ilk.getOptions()[i]==double[].class) {
          value = value.replaceAll("\\[","").replaceAll("]","");
          String[] values = value.split(";");
          double[] v = new double[values.length];
          for(int j=0; j<v.length; j++) {
            v[j] = Double.parseDouble(values[j]);
          }
          args[i] = v;
        } else if(ilk.getOptions()[i]==float[].class) {
          value = value.replaceAll("\\[","").replaceAll("]","");
          String[] values = value.split(";");
          float[] v = new float[values.length];
          for(int j=0; j<v.length; j++) {
            v[j] = Float.parseFloat(values[j]);
          }
          args[i] = v;
        } else if(ilk.getOptions()[i]==int[].class) {
          value = value.replaceAll("\\[","").replaceAll("]","");
          String[] values = value.split(";");
          int[] v = new int[values.length];
          for(int j=0; j<v.length; j++) {
            v[j] = Integer.parseInt(values[j]);
          }
          args[i] = v;
        } else if(ilk.getOptions()[i]==long[].class) {
          value = value.replaceAll("\\[","").replaceAll("]","");
          String[] values = value.split(";");
          long[] v = new long[values.length];
          for(int j=0; j<v.length; j++) {
            v[j] = Long.parseLong(values[j]);
          }
          args[i] = v;
        } else if(ilk.getOptions()[i]==boolean[].class) {
          value = value.replaceAll("\\[","").replaceAll("]","");
          String[] values = value.split(";");
          boolean[] v = new boolean[values.length];
          for(int j=0; j<v.length; j++) {
            v[j] = Boolean.parseBoolean(values[j]);
          }
          args[i] = v;
        } else if(ilk.getOptions()[i]==char[].class) {
          value = value.replaceAll("\\[","").replaceAll("]","");
          String[] values = value.split(";");
          char[] v = new char[values.length];
          for(int j=0; j<v.length; j++) {
            v[j] = values[j].charAt(0);
          }
          args[i] = v;
        } else if(ilk.getOptions()[i]==byte[].class) {
          value = value.replaceAll("\\[","").replaceAll("]","");
          String[] values = value.split(";");
          byte[] v = new byte[values.length];
          for(int j=0; j<v.length; j++) {
            v[j] = (byte)Integer.parseInt(values[j]);
          }
          args[i] = v;
        } else if(ilk.getOptions()[i].isArray()) {
          value = value.replaceAll("\\[","").replaceAll("]","");
          String[] values = value.split(";");
          Object[] arr = (Object[])Array.newInstance(ilk.getOptions()[i].getComponentType(),values.length);
          for(int j=0; j<arr.length; j++) {
            arr[j] = objectMap.get(values[j]);
          }
          args[i] = arr;
        } else {
          args[i] = objectMap.get(value);
        }
      }
      Object instance = loader.classForIlk(ilk).getConstructor(ilk.getOptions()).newInstance(args);
      objectMap.put(name,instance);
    }
  }
  
  public void render() {
    Viewer viewer = null;
    for(Object object : objectMap.values()) {
      if(object instanceof Viewer) {
        viewer = (Viewer)object;
        break;
      }
    }
    JFrame frame = new JFrame("Rendered");
    BufferedImage img = new BufferedImage(viewer.getWidth()*viewer.getDensity(),viewer.getHeight()*viewer.getDensity(),BufferedImage.TYPE_INT_RGB);
  
    img.setRGB(0,0,viewer.getWidth()*viewer.getDensity(),viewer.getHeight()*viewer.getDensity(),viewer.getViewColorArray(10),0,viewer.getDensity()*viewer.getWidth());
  
    frame.add(new JLabel(new ImageIcon(img)));
  
    frame.setSize(600,600);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  public static void main(String[] args) throws FileNotFoundException {
    SceneParser parser = new SceneParser();
    try {
      parser.load();
      parser.render();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (IlkNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    }
  }
  
}
