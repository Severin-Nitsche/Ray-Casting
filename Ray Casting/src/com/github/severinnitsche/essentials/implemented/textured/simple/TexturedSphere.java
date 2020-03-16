package com.github.severinnitsche.essentials.implemented.textured.simple;

import com.github.severinnitsche.essentials.implemented.basic.Sphere;
import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.essentials.meta.help.ObjectInformation;
import com.github.severinnitsche.utilities.math.MathUtil;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.visual.Color;
import com.github.severinnitsche.utilities.visual.Texture;

public class TexturedSphere extends Sphere {
  
  public TexturedSphere(Point position, double radius, Texture texture, double roughness) {
    super(position,radius,texture,roughness);
  }
  
  @Override
  public ObjectInformation collide(Ray ray) {
    ObjectInformation info = super.collide(ray);
  
    if(info==null) return null;
    
    Vector normal = info.normal;
    
    info.u = normal.getSpherical(1)/MathUtil.TAU+.5;//Math.atan2(normal.get(0),normal.get(2)) / MathUtil.TAU + .5;//normal.get(0)/2+.5;//Math.asin(normal.get(0))/ MathUtil.PI+.5;
    info.v = normal.getSpherical(0)/MathUtil.PI;//normal.get(1) * .5 + .5;//normal.get(1)/2+.5;//Math.asin(normal.get(1))/ MathUtil.PI+.5;
    
    //System.out.println(normal);
    //System.out.println("u: "+tu);
    //System.out.println("v: "+tv);
    
    info.c = new Color(((Texture)this.color).to(info.u,info.v).getRGB());
    
    return info;
  }
  
}
