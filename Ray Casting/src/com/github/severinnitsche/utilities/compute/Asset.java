package com.github.severinnitsche.utilities.compute;

import com.github.severinnitsche.essentials.abstracted.ThreeDObject;
import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.essentials.meta.help.ObjectInformation;
import com.github.severinnitsche.essentials.meta.help.World;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;

import static com.github.severinnitsche.essentials.meta.help.World.SURFACE_DISTANCE;

public class Asset {
  
  public static ObjectInformation dist(Point position, Vector dir, World world) {
    Ray check = new Ray(position, dir);
    double record = Double.POSITIVE_INFINITY;
    ObjectInformation info = null;
    for (ThreeDObject object : world) {
      ObjectInformation temporaryData = object.collide(check);
      if(temporaryData==null) continue;
      if (temporaryData.distance < record && temporaryData.distance > SURFACE_DISTANCE) {
        record = temporaryData.distance;
        info = temporaryData;
      }
    }
    return info;
  }
  
  public static Vector reflection(Vector normal, Vector direction) {
    return direction.reject(normal).subtract(direction.project(normal));
  }
  
}
