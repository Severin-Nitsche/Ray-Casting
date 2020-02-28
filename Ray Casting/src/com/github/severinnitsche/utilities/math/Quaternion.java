package com.github.severinnitsche.utilities.math;

public class Quaternion {
  
  protected double r,i,j,k;
  
  public Quaternion(double r, double i, double j, double k) {
    this.r = r;
    this.i = i;
    this.j = j;
    this.k = k;
  }
  
  public Quaternion(Quaternion q) {
    this.r = q.r;
    this.i = q.i;
    this.j = q.j;
    this.k = q.k;
  }
  
  public Quaternion(double s, Vector v) {
    if(v.dimensions()!=3) throw new IllegalStateException("v has to have 3 dimensions");
    this.r = s;
    this.i = v.get(0);
    this.j = v.get(1);
    this.k = v.get(2);
  }
  
  public Quaternion(Vector v) {
    this(0,v);
  }
  
  public Quaternion(Point p) {
    this(1,new Vector(p));
  }
  
  public Quaternion multiply(Quaternion b) {
    double tr = r*b.r - i*b.i - j*b.j - k*b.k;
    double ti = r*b.i + i*b.r + j*b.k - k*b.j;
    double tj = r*b.j - i*b.k + j*b.r + k*b.i;
    double tk = r*b.k + i*b.j - j*b.i + k*b.r;
    
    this.r = tr;
    this.i = ti;
    this.j = tj;
    this.k = tk;
    
    return this;
  }
  
  public Quaternion multiply(double d) {
    this.r *= d;
    this.i *= d;
    this.j *= d;
    this.k *= d;
    
    return this;
  }
  
  public static Quaternion multiply(Quaternion a, Quaternion b) {
    Quaternion q = new Quaternion(a);
    return q.multiply(b);
  }
  
  public static Quaternion multiply(Quaternion a, double d) {
    Quaternion q = new Quaternion(a);
    return q.multiply(d);
  }
  
  public Quaternion divide(Quaternion b) {
    double tr = r/b.r - i/b.i - j/b.j - k/b.k;
    double ti = r/b.i + i/b.r + j/b.k - k/b.j;
    double tj = r/b.j - i/b.k + j/b.r + k/b.i;
    double tk = r/b.k + i/b.j - j/b.i + k/b.r;
  
    this.r = tr;
    this.i = ti;
    this.j = tj;
    this.k = tk;
  
    return this;
  }
  
  public Quaternion divide(double d) {
    this.r /= d;
    this.i /= d;
    this.j /= d;
    this.k /= d;
    
    return this;
  }
  
  public static Quaternion divide(Quaternion a, Quaternion b) {
    Quaternion q = new Quaternion(a);
    return q.divide(b);
  }
  
  public static Quaternion divide(Quaternion a, double d) {
    Quaternion q = new Quaternion(a);
    return q.divide(d);
  }
  
  public Quaternion add(Quaternion b) {
    this.r += b.r;
    this.i += b.i;
    this.j += b.j;
    this.k += b.k;
    
    return this;
  }
  
  public Quaternion add(double d) {
    this.r += d;
    
    return this;
  }
  
  public static Quaternion add(Quaternion a, Quaternion b) {
    Quaternion q = new Quaternion(a);
    return q.add(b);
  }
  
  public static Quaternion add(Quaternion a, double d) {
    Quaternion q = new Quaternion(a);
    return q.add(d);
  }
  
  public Quaternion subtract(Quaternion b) {
    this.r -= b.r;
    this.i -= b.i;
    this.j -= b.j;
    this.k -= b.k;
    
    return this;
  }
  
  public Quaternion subtract(double d) {
    this.r -= d;
    
    return this;
  }
  
  public static Quaternion subtract(Quaternion a, Quaternion b) {
    Quaternion q = new Quaternion(a);
    return q.subtract(b);
  }
  
  public static Quaternion subtract(Quaternion a, double d) {
    Quaternion q = new Quaternion(a);
    return q.subtract(d);
  }
  
  public Vector getVector() {
    return new Vector(i,j,k);
  }
  
  public double getScalar() {
    return r;
  }
  
  public static Quaternion invert(Quaternion q) {
    Quaternion n = new Quaternion(q.getScalar(),q.getVector().multiply(-1));
    double d = q.getScalar()*q.getScalar() + q.getVector().squaredMagnitude();
    return n.divide(d);
  }
  
  public Quaternion invert() {
    Quaternion inverted = invert(this);
    
    this.r = inverted.r;
    this.i = inverted.i;
    this.j = inverted.j;
    this.k = inverted.k;
    
    return this;
  }
  
  public double squaredMagnitude() {
    return r*r + i*i + j*j + k*k;
  }
  
  @Override
  public String toString() {
    return r + " + " + i + "i + " + j + "j + " + k + "k";
  }
  
}
