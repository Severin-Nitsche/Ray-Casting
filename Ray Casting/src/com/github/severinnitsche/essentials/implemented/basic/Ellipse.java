package com.github.severinnitsche.essentials.implemented.basic;

import com.github.severinnitsche.essentials.abstracted.ThreeDObject;
import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.essentials.meta.help.ObjectInformation;
import com.github.severinnitsche.utilities.math.Matrix;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.math.Versor;
import com.github.severinnitsche.utilities.math.exceptions.MatrixConversionException;
import com.github.severinnitsche.utilities.visual.Color;

public class Ellipse implements ThreeDObject {
  
  protected Point center;
  protected Matrix conversion;
  
  protected Color color;
  protected double roughness;
  
  public Ellipse(Color color, double roughness, Point pos, Versor r, double a, double b, double c) {
    this.center = new Point(pos);
    
    //ı = alt + shift + J
    
    Vector xı = r.rotate(new Vector(1,0,0).divide(a));
    Vector yı = r.rotate(new Vector(0,1,0).divide(b));
    Vector zı = r.rotate(new Vector(0,0,1).divide(c));
    
    conversion = new Matrix(xı,yı,zı,new Point(0,0,0));
    
    this.color = color;
    this.roughness = roughness;
  }
  
  @Override
  public ObjectInformation collide(Ray ray) {
    try {
      Vector vı = conversion.translate(ray.getDirection());
      Vector pı = conversion.translate(ray.getPosition()).subtract(conversion.translate(center));
      
      double pı2 = pı.squaredMagnitude();
      double vı2 = vı.squaredMagnitude();
      double vıpı = pı.dot(vı);
  
      double radical = (1-pı2)/vı2+Math.pow(vıpı,2)/Math.pow(vı2,2);
  
      if(radical<0) return null;
  
      double constant = vıpı/vı2;
  
      double t1 = Math.sqrt(radical) - constant;
      double t2 = -Math.sqrt(radical) - constant;
  
      double distance = t2<0?t1:t2;
      Color c = new Color(color);
      Point pos = ray.getPosition().add(ray.getDirection().multiply(distance));
      Vector normal = getNormalAt(pos);
      
      return new ObjectInformation(pos,normal,c,this,distance);
    } catch (MatrixConversionException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public double roughness() {
    return roughness;
  }
  
  @Override
  public Vector getNormalAt(Point p) {
    return p.subtract(center).normalize();
  }
  
  @Override
  public double reflectance() {
    return 1;
  }
  
}