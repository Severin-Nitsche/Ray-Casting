package com.github.severinnitsche.essentials.meta.help;

import com.github.severinnitsche.essentials.meta.lights.abstracted.Light;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.visual.Color;

public class LightInfo {
  private double[] lightLevel;
  private Light[] lights;
  private Vector[] directions;
  public LightInfo(double[] lightLevel, Light[] lights, Vector[] directions) {
    if(lightLevel.length != lights.length || lights.length != directions.length) throw new IllegalStateException("Lengths must be equal!");
    this.lightLevel = lightLevel;
    this.lights = lights;
    this.directions = directions;
  }
  public Color litColor(Color c) {
    if(lightLevel.length != lights.length) throw new IllegalStateException("Lengths must be equal!");
    if(c==null) return Color.black();
    Color ret = Color.black();
    for(int i=0; i<lights.length; i++) {
      Color temp = new Color(c.getRGB());
      temp.mult(lightLevel[i]);
      temp.limit(lights[i].getColor(directions[i]));
      ret.add(temp);
    }
    return ret;
  }
}
