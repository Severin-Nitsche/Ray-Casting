package com.github.severinnitsche.essentials.meta.help;

import com.github.severinnitsche.essentials.abstracted.ThreeDObject;
import com.github.severinnitsche.utilities.math.MultiDimensional;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.visual.Color;

public class ObjectInformation {
  Point position;
  Vector normal;
  Color c;
  ThreeDObject o;
  public double distance;

  public ObjectInformation(Point position, Vector normal, Color c, ThreeDObject o, double distance) {
    this.position = position;
    this.normal = normal;
    this.c = new Color(c.getRGB());
    this.o = o;
    this.distance = distance;
  }
}
