package com.github.severinnitsche.essentials.meta.lights.abstracted;

import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.visual.Color;

public interface Light {
  public static final double SURFACE_DISTANCE = .01;
  
  double hasLightLevel(Point p);
  Vector[] getDirections(int samples);
  Color getColor(Vector v);
  double getStrength();
  Point getPosition();
  Vector getDirTo(Point p);
}
