package com.github.severinnitsche.essentials.meta;

import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;

public class Ray {

  private Point position;
  private Vector direction;

  public Ray(Point position, Vector direction) {
    this.position = position;
    this.direction = direction.normalizeN();
  }

  public Ray(double x, double y, double z, double direction1, double direction2) {
    this.position = new Point(x,y,z);
    this.direction = Vector.fromSpherical(direction1,direction2,1);
  }

  public Point getPosition() {
    return new Point(position);
  }

  public Vector getDirection() {
    return new Vector(direction);
  }

}
