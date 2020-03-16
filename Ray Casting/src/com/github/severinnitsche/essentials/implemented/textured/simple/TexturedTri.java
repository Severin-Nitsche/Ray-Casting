package com.github.severinnitsche.essentials.implemented.textured.simple;

import com.github.severinnitsche.essentials.implemented.limited.Tri;
import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.essentials.meta.help.ObjectInformation;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.visual.Color;
import com.github.severinnitsche.utilities.visual.Texture;

public class TexturedTri extends Tri {
  
  public TexturedTri(Point a, Point b, Point c, Texture texture, double roughness) {
    super(a, b, c, texture, roughness);
  }
  
  @Override
  public ObjectInformation collide(Ray ray) {
    ObjectInformation info = super.collide(ray);
    
    if(info == null) return null;
    
    Vector p = info.position.subtract(position);
  
    info.u = Math.sqrt(p.project(ac).squaredMagnitude()) / Math.sqrt(ac.squaredMagnitude());
    info.v = Math.sqrt(p.project(ab).squaredMagnitude()) / Math.sqrt(ab.squaredMagnitude());
    
    info.c = new Color(((Texture)color).to(info.u,info.v).getRGB());
    
    return info;
  }
}
