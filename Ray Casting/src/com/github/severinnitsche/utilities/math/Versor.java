package com.github.severinnitsche.utilities.math;

public class Versor extends Quaternion {
  
  public static final double ZERO = .01;
  
  private double theta;
  
  public Versor(Vector axis, double theta) {
    super(Math.cos(theta / 2), axis.multiplyN(Math.sin(theta)));
    this.theta = theta;
  }
  
  public Vector rotate(Vector v) {
    Quaternion q = new Quaternion(v);
    return Quaternion.multiply(this, q).multiply(Quaternion.invert(this)).getVector();
  }
  
  public Point rotate(Point p) {
    Quaternion q = new Quaternion(p);
    return new Point(Quaternion.multiply(this, q).multiply(Quaternion.invert(this)).getVector());
  }
  
  private Quaternion operation(Quaternion n) {
    if (Math.abs(n.squaredMagnitude() - 1) < ZERO) throw new IllegalStateException("This must retain length one!");
  
    this.r = n.r;
    this.i = n.i;
    this.j = n.j;
    this.k = n.k;
  
    this.theta = Math.acos(r) * 2;
  
    return this;
  }
  
  @Override
  public Quaternion add(Quaternion b) {
    return operation(Quaternion.add(this, b));
  }
  
  @Override
  public Quaternion add(double d) {
    return operation(Quaternion.add(this, d));
  }
  
  @Override
  public Quaternion subtract(Quaternion b) {
    return operation(Quaternion.subtract(this, b));
  }
  
  @Override
  public Quaternion subtract(double d) {
    return operation(Quaternion.subtract(this, d));
  }
  
  @Override
  public Quaternion multiply(Quaternion b) {
    return operation(Quaternion.multiply(this, b));
  }
  
  @Override
  public Quaternion multiply(double d) {
    return operation(Quaternion.multiply(this, d));
  }
  
  @Override
  public Quaternion divide(Quaternion b) {
    return operation(Quaternion.divide(this, b));
  }
  
  @Override
  public Quaternion divide(double d) {
    return operation(Quaternion.divide(this, d));
  }
  
}
