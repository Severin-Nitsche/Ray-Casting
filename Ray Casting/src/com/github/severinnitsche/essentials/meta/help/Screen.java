package com.github.severinnitsche.essentials.meta.help;

import com.github.severinnitsche.essentials.implemented.ThreeDPlane;
import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;

public class Screen extends ThreeDPlane {

  public static final double ZERO = .00001;

  private Vector x;
  private Vector y;

  public Screen(Point position, Vector normal, Vector x) {
    super(position, normal.normalize(), null, 0, false);
    if(Math.abs(normal.dot(x)) > ZERO) throw new IllegalArgumentException("normal and x have to be orthogonal!");
    this.x = x.normalize();
    this.y = x.cross(normal).normalize();
  }

  public Point getPosition() {
    return new Point(position);
  }

  public Vector getX() {
    return new Vector(x);
  }

  public Vector getY() {
    return new Vector(y);
  }

  public Point convert( double x, double y ) {
    Point g = getPosition().addN(getX().multiply(x)).add(getY().multiply(y));
    return g;
  }

  public Point convert(Point p) {
    return convert(p.get(0),p.get(1));
  }

  public Ray get(Point origin, Point local) {
    //local -> global
    Point lg = convert(local);
    return new Ray(origin, lg.subtract(origin));
  }

}
