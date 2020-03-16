package com.github.severinnitsche.essentials.meta.lights.abstracted;

import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.visual.Color;

public interface Light {
  double hasLightLevel(Point p);
  Vector[] getDirections(int samples);
  Color getColor(Vector v);
  double getStrength();
  Point getPosition();
  Vector getDirTo(Point p);
}
