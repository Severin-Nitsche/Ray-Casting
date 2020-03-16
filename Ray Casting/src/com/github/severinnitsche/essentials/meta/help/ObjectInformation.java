package com.github.severinnitsche.essentials.meta.help;

import com.github.severinnitsche.essentials.abstracted.ThreeDObject;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.visual.Color;

public class ObjectInformation {
  public Point position;
  public Vector normal;
  public Color c;
  public ThreeDObject o;
  public double distance;
  public double u;
  public double v;

  public ObjectInformation(Point position, Vector normal, Color c, ThreeDObject o, double distance, double u, double v) {
    this.position = position;
    this.normal = normal;
    this.c = new Color(c.getRGB());
    this.o = o;
    this.distance = distance;
    this.u = u;
    this.v = v;
  }
  
  public ObjectInformation(Point position, Vector normal, Color c, ThreeDObject o, double distance) {
    this(position,normal,c,o,distance,-1,-1);
  }
}
