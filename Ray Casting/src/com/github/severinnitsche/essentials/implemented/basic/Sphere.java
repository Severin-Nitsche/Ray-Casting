package com.github.severinnitsche.essentials.implemented.basic;

import com.github.severinnitsche.essentials.abstracted.ThreeDObject;
import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.essentials.meta.help.ObjectInformation;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.visual.Color;

public class Sphere implements ThreeDObject {

  protected Color color;
  protected double reflectance;
  protected double roughness;

  protected double radius;
  protected Point center;

  public Sphere(Point position, double radius, Color color, double roughness) {
    this.color = color;
    this.reflectance = 1;
    this.roughness = roughness;
    this.radius = radius;
    this.center = position;
  }
  
  public Sphere(Point position, double radius, Color color, double reflectance, double roughness) {
    if(reflectance > 1 || reflectance < 0) throw new IllegalArgumentException("reflectance needs to be in range 1 (0% reflectance) to 0 (100% reflectance)");
    this.color = color;
    this.reflectance = reflectance;
    this.roughness = roughness;
    this.radius = radius;
    this.center = position;
  }

  @Override
  public ObjectInformation collide(Ray ray) {
    
    Point c = new Point(center);
    double r = radius;
    Vector l = ray.getDirection();
    Point o = ray.getPosition();

    Vector co = o.subtract(c);

    double radicand = Math.pow(l.dot(co),2) - (co.squaredMagnitude()-r*r);

    if(radicand<0) return null;

    double d1 = -l.dot(co) + Math.sqrt(radicand);
    double d2 = -l.dot(co) - Math.sqrt(radicand);



    //getting collision Point
    Point collision = o.addN(l.multiplyN(d2 >= 0 ? d2 : d1));

    //calculating normal
    Vector normal = collision.subtract(c);

    ObjectInformation info = new ObjectInformation(collision,normal,this.color,this,d2 >= 0 ? d2 : d1);

    return info;
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
    return reflectance;
  }
  
  @Override
  public boolean equals(Object o) {
    if(o instanceof Sphere) {
      return ((Sphere)o).color.equals(color) && ((Sphere)o).reflectance == reflectance && ((Sphere)o).radius == radius && ((Sphere)o).center.equals(center);
    }
    return false;
  }

  @Override
  public String toString() {
    return "ref: "+reflectance+"\ncol: "+color+"\nrad: "+radius+"\ncen: "+center;
  }

}
