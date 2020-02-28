package com.github.severinnitsche.utilities.math;

public class MultiDimensional {
  public static final int X = 0;
  public static final int THETA = 0;
  public static final int Y = 1;
  public static final int PHI = 1;
  public static final int Z = 2;
  public static final int RHO = 2;
  public static final int W = 3;

  private double[] dimension;

  public MultiDimensional(double...dimension) {
    this.dimension = dimension;
  }

  public MultiDimensional(MultiDimensional v) {
    this.dimension = new double[v.dimensions()];
    for(int i=0; i<v.dimensions(); i++) {
      this.dimension[i] = v.dimension[i];
    }
  }

  public int dimensions() {
    return dimension.length;
  }

  public double get(int d) {
    if(d<0^d>=dimension.length) throw new IllegalArgumentException("d must be less than dimensions and greater than 0");
    return dimension[d];
  }

  public void set(int d, double v) {
    if(d>=dimension.length) throw new IllegalArgumentException("d must be less than dimensions");
    dimension[d] = v;
  }

  public double getSpherical(int d) {
    if(d<0^d>3) throw new IllegalArgumentException("d must be between 0 and 2");
    double rho = Math.sqrt(dimension[X] * dimension[X] + dimension[Y] * dimension[Y] + dimension[Z] * dimension[Z]);
    double phi = Math.atan2(dimension[Y], dimension[X]);
    double theta = Math.acos(dimension[Z] / rho);
    double[] returnValue = {theta, phi, rho};
    return returnValue[d];
  }
  
  public void setSpherical(int d, double v) {
    double rho = Math.sqrt(dimension[X] * dimension[X] + dimension[Y] * dimension[Y] + dimension[Z] * dimension[Z]);
    double phi = Math.atan2(dimension[Y], dimension[X]);
    double theta = Math.acos(dimension[Z] / rho);
    double[] that = {theta, phi, rho};
    that[d] = v;
    dimension[X] = that[2] * Math.sin(that[0]) * Math.cos(that[1]);
    dimension[Y] = that[2] * Math.sin(that[0]) * Math.sin(that[1]);
    dimension[Z] = that[2] * Math.cos(that[0]);
  }

  @Override
  public boolean equals(Object o) {
    if(o instanceof MultiDimensional) {
      return ((MultiDimensional)o).dimension.equals(dimension);
    }
    return false;
  }

  @Override public String toString() {
    String ret = "";
    for(int i=0; i<dimensions(); i++) ret+="\t"+get(i);
    return ret;
  }
}
