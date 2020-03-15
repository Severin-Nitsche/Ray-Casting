package com.github.severinnitsche.essentials.implemented.limited;

import com.github.severinnitsche.essentials.implemented.basic.ThreeDPlane;
import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.essentials.meta.help.ObjectInformation;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.visual.Color;

public class Tri extends ThreeDPlane {
  
  protected Vector ab;
  protected Vector ac;
  
  public Tri(Point a, Point b, Point c, Color color, double roughness) {
    this.position = a;
    this.ab = b.subtract(a);
    this.ac = c.subtract(a);
    this.normal = ab.cross(ac).normalize();
    this.color = color;
    this.reflectance = 1;
    this.roughness = roughness;
  }
  
  @Override
  public ObjectInformation collide(Ray ray) {
    ObjectInformation info = super.collide(ray);
    
    if(info == null) return null;
    
    Point p = info.position;
    Vector ap = p.subtract(position);
  
    double xr = Math.sqrt(ap.project(ac).squaredMagnitude())/Math.sqrt(ac.squaredMagnitude());
    double yr = Math.sqrt(ap.project(ab).squaredMagnitude())/Math.sqrt(ab.squaredMagnitude());
    
    if(xr<1&&0<xr&&yr<1&&0<yr&&ap.project(ac).dot(ac)>0&&ap.project(ab).dot(ab)>0) {
      return info;
    }
    
    return null;
  }
  
}
