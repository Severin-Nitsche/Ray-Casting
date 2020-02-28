package com.github.severinnitsche.utilities.math;

public class Vector extends MultiDimensional {

  public Vector(double...dimension) {
    super(dimension);
  }

  public Vector(MultiDimensional v) {
    super(v);
  }

  public static Vector fromSpherical(double theta, double phi, double rho) {
    double x = rho * Math.sin(theta) * Math.cos(phi);
    double y = rho * Math.sin(theta) * Math.sin(phi);
    double z = rho * Math.cos(theta);
    return new Vector(x,y,z);
  }

  public Vector add(Vector v) {
    if(v.dimensions()!=dimensions()) throw new IllegalArgumentException("Dimension must be of equal length!");
    for(int i=0; i<dimensions(); i++) {
      set(i,get(i)+v.get(i));
    }
    return this;
  }

  public Vector subtract(Vector v) {
    if(v.dimensions()!=dimensions()) throw new IllegalArgumentException("Dimension must be of equal length!");
    for(int i=0; i<dimensions(); i++) {
      set(i,get(i)-v.get(i));
    }
    return this;
  }

  public Vector multiply(double s) {
    for(int i=0; i<dimensions(); i++) {
      set(i,get(i)*s);
    }
    return this;
  }

  public Vector divide(double s) {
    for(int i=0; i<dimensions(); i++) {
      set(i,get(i)/s);
    }
    return this;
  }

  public Vector normalize() {
    return divide(getSpherical(RHO));
  }

  public double dot(Vector v) {
    if(v.dimensions()!=dimensions()) throw new IllegalArgumentException("Dimension must be of equal length!");
    double ret = 0;
    for(int i=0; i<dimensions(); i++) {
      ret += get(i)*v.get(i);
    }
    return ret;
  }

  public double squaredMagnitude() {
    return this.dot(this);
  }

  public Vector cross(Vector v) {
    if(v.dimensions()!=3&&dimensions()!=3) throw new IllegalArgumentException("Dimensions must be of length 3!");
    return new Vector(get(1)*v.get(2)-get(2)*v.get(1), get(2)*v.get(0)-get(0)*v.get(2), get(0)*v.get(1)-get(1)*v.get(0));
  }

  public Vector project(Vector b) {
    return new Vector(b).multiply(dot(b)/b.squaredMagnitude());
  }

  public Vector reject(Vector b) { return new Vector(this).subtract(project(b));}

  public Vector addN(Vector v) {
    if(v.dimensions()!=dimensions()) throw new IllegalArgumentException("Dimension must be of equal length!");
    Vector ret = new Vector(this);
    for(int i=0; i<dimensions(); i++) {
      ret.set(i,get(i)+v.get(i));
    }
    return ret;
  }

  public Vector subtractN(Vector v) {
      if(v.dimensions()!=dimensions()) throw new IllegalArgumentException("Dimension must be of equal length!");
      Vector ret = new Vector(this);
      for(int i=0; i<dimensions(); i++) {
        ret.set(i,get(i)-v.get(i));
      }
      return ret;
  }

  public Vector multiplyN(double s) {
    Vector ret = new Vector(this);
    for(int i=0; i<dimensions(); i++) {
      ret.set(i,get(i)*s);
    }
    return ret;
  }

  public Vector divideN(double s) {
    Vector ret = new Vector(this);
    for(int i=0; i<dimensions(); i++) {
      ret.set(i,get(i)/s);
    }
    return ret;
  }

  public Vector normalizeN() {
    return divideN(getSpherical(RHO));
  }

}
