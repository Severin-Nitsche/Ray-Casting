package com.github.severinnitsche.essentials.implemented.basic;

import com.github.severinnitsche.essentials.abstracted.ThreeDObject;
import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.essentials.meta.help.ObjectInformation;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.visual.Color;

public class ThreeDPlane implements ThreeDObject {

  protected Color color;
  protected double reflectance;
  protected double roughness;
  protected Vector normal;
  protected Point position;

  protected ThreeDPlane() {

  }

  public ThreeDPlane(Point position, Vector normal, Color color, double reflectance, double roughness) {
    this.position = position;
    this.normal = normal;
    this.color = color;
    this.reflectance = reflectance;
    this.roughness = roughness;
  }
  
  public ThreeDPlane(Point position, Vector normal, Color color, double roughness) {
    this.position = position;
    this.normal = normal;
    this.color = color;
    this.reflectance = 1;
    this.roughness = roughness;
  }

  public Point getPosition() {
    return new Point(position);
  }

  public Vector getNormal() {
    return new Vector(normal);
  }

  /**
  *
  * <p>Formula used for intersection detection:</p>
  * <a href="https://en.wikipedia.org/wiki/Line%E2%80%93plane_intersection#Algebraic_form">wikipedia</a>
  *
  * <style>
  * .warning {
  *  color: red;
  *  background-color: #FFACAC;
  *  font-weight: bold;
  *  border: 2pt solid red;
  * }
  * </style>
  *
  * <p class="warning">!!!IMPORTANT!!! in the following  '*' denotes the dot product.</p>
  * <p><i>d</i><b>l</b> + <b>l<sub>0</sub></b></p>
  * <p>where <i>d</i> equals ( (<b>p<sub>0</sub></b> - <b>l<sub>0</sub></b>) * <b>n</b> ) / ( <b>l</b> * <b>n</b> )</p>
  * <p>and</p>
  * <p><b>n</b> -&gt; normal</p>
  * <p><b>p<sub>0</sub></b> -&gt; generic Point on the plane</p>
  * <p><b>l</b> -&gt; direction Vector of the line</p>
  * <p><b>l<sub>0</sub></b> -&gt; generic Point on the line</p>
  * <p><i>d</i> -&gt; scalar</p>
  *
   * @return
   */
  @Override
  public ObjectInformation collide(Ray ray) {
    Point p0 = position;
    Point l0 = ray.getPosition();
    Vector n = new Vector(normal);
    Vector l = ray.getDirection();
    double denominator = l.dot(n);
    if (denominator == 0) return null;
    double numerator = p0.subtract(l0).dot(n);
    double d = numerator / denominator;

    Point collision = l0.add(l.multiplyN(d));

    ObjectInformation info = new ObjectInformation(collision,n,new Color(this.color.getRGB()),this,d);

    return info;
  }

  @Override
  public double roughness() {
    return roughness;
  }

  @Override
  public Vector getNormalAt(Point p) {
    return new Vector(normal);
  }
  
  @Override
  public double reflectance() {
    return reflectance;
  }
  
}
