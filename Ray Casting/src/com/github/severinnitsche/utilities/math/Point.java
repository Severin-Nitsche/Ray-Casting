package com.github.severinnitsche.utilities.math;

public class Point extends MultiDimensional{

  public Point(double...dimension) {
    super(dimension);
  }
  public Point(MultiDimensional v) { super(v);}

  public static Point fromSpherical(double theta, double phi, double rho) {
    double x = rho * Math.sin(theta) * Math.cos(phi);
    double y = rho * Math.sin(theta) * Math.sin(phi);
    double z = rho * Math.cos(theta);
    return new Point(x,y,z);
  }
  
  public Matrix asColumn() {
    double[][] m = new double[4][1];
    for(int i=0; i<3; i++) {
      m[i][0] = this.get(i);
    }
    m[3][0] = 1;
    return new Matrix(m);
  }
  
  public Matrix asRow() {
    double[][] m = new double[1][4];
    for(int i=0; i<3; i++) {
      m[0][i] = this.get(i);
    }
    m[0][3] = 1;
    return new Matrix(m);
  }
  
  public Vector asVector() {
    return new Vector(this);
  }

  public Point add(Vector v) {
    if(v.dimensions()!=dimensions()) throw new IllegalArgumentException("Dimension must be of equal length!");
    for(int i=0; i<dimensions(); i++) {
      set(i,get(i)+v.get(i));
    }
    return this;
  }

  public Point subtract(Vector v) {
    if(v.dimensions()!=dimensions()) throw new IllegalArgumentException("Dimension must be of equal length!");
    for(int i=0; i<dimensions(); i++) {
      set(i,get(i)-v.get(i));
    }
    return this;
  }

  public Vector subtract(Point p) {
    if(p.dimensions()!=dimensions()) throw new IllegalArgumentException("Dimension must be of equal length!");
    Vector ret = new Vector(this);
    for(int i=0; i<dimensions(); i++) {
      ret.set(i,get(i)-p.get(i));
    }
    return ret;
  }

  public Point addN(Vector v) {
    if(v.dimensions()!=dimensions()) throw new IllegalArgumentException("Dimension must be of equal length!");
    Point ret = new Point(this);
    for(int i=0; i<dimensions(); i++) {
      ret.set(i,get(i)+v.get(i));
    }
    return ret;
  }

  public Point subtractN(Vector v) {
    if(v.dimensions()!=dimensions()) throw new IllegalArgumentException("Dimension must be of equal length!");
    Point ret = new Point(this);
    for(int i=0; i<dimensions(); i++) {
      ret.set(i,get(i)-v.get(i));
    }
    return ret;
  }

}
