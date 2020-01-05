package com.github.severinnitsche.utilities.math;

public class MathUtil {

  public static final double GOLDENANGLE = 2.39996322972865332;
  public static final double GOLDENRATIO = 1.618033988738303;


  public static double map(double value, double min, double max, double nmin, double nmax) {
    if(value<min||value>max) System.out.println("Warning: out of bounds");
    double elevated = value - min;
    double elevatedMax = max - min;
    double p = elevated / elevatedMax;
    double nelevatedmax = nmax - nmin;
    double nelevated = nelevatedmax * p;
    double n = nelevated + nmin;
    return n;
  }

  public static byte min(byte a, byte b) {
    return a<b?a:b;
  }

  public static int min(int a, int b) {
    return a<b?a:b;
  }

  public static double clamp(double value, double min, double max) {
    if(value<min) return min;
    if(value>max) return max;
    return value;
  }

  public static String vecToString(double[] a) {
    return "x:\t"+a[0]+"\r\n"+
           "y:\t"+a[1]+"\r\n"+
           "z:\t"+a[2]+"\r\n";
  }

  public static Vector[] getVectorsOnUnitSphere(int n) {
    Vector[] vectors = new Vector[n];
    for(int i=0; i<n; i++) {
      double theta = i * GOLDENANGLE;
      double phi = Math.asin(2*(double)i/(double)n - 1);
      double rho = 1;
      vectors[i] = Vector.fromSpherical(theta,phi,rho);
    }
    return vectors;
  }

  public static Point[] getPointsOnUnitSphere(int n) {
    Point[] points = new Point[n];
    for(int i=0; i<n; i++) {
      double theta = i * GOLDENANGLE;
      double phi = Math.asin(2*(double)i/(double)n - 1);
      double rho = 1;
      points[i] = Point.fromSpherical(theta,phi,rho);
    }
    return points;
  }

}
