package com.github.severinnitsche.essentials.abstracted;

import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.essentials.meta.help.ObjectInformation;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;

public interface ThreeDObject {
  ObjectInformation collide(Ray ray);
  /**
  *
  * @return value between 0 (0% / no roughness) and 1 (100% / total roughness)
  *
  */
  double roughness();

  Vector getNormalAt(Point p);
  
  double reflectance();
}
