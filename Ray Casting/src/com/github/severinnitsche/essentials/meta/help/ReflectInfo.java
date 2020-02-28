package com.github.severinnitsche.essentials.meta.help;

import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.visual.Color;

public class ReflectInfo {
  public Vector direction;
  public int depth;
  public Point position;
  public Color color;
  public double strength;
  
  public ReflectInfo(Vector direction, int depth, Point position, Color color, double strength){
    this.direction = direction;
    this.depth = depth;
    this.position = position;
    this.color = new Color(color.getRGB());
    this.strength = strength;
  }
  
  public static ReflectInfo[] fill(Vector[] directions, int depth, Point position, Color color, double strength) {
    ReflectInfo[] fill = new ReflectInfo[directions.length];
    for(int i=0; i<fill.length; i++) {
      fill[i] = new ReflectInfo(directions[i],depth,position,color,strength);
    }
    return fill;
  }
}
