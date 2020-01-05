package com.github.severinnitsche.essentials.meta.help;

import com.github.severinnitsche.essentials.meta.Light;
import com.github.severinnitsche.utilities.visual.Color;

public class LightInfo {
  private double[] lightLevel;
  private Light[] lights;
  public LightInfo(double[] lightLevel, Light[] lights) {
    if(lightLevel.length != lights.length) throw new IllegalStateException("Lengths must be equal!");
    this.lightLevel = lightLevel;
    this.lights = lights;
  }
  public Color litColor(Color c) {
    if(lightLevel.length != lights.length) throw new IllegalStateException("Lengths must be equal!");
    if(c==null) return Color.black();
    Color ret = Color.black();
    for(int i=0; i<lights.length; i++) {
      Color temp = new Color(c.getRGB());
      temp.mult(lightLevel[i]);
      temp.limit(lights[i].color);
      ret.add(temp);
    }
    return ret;
  }
}
