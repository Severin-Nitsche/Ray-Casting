package com.github.severinnitsche.essentials.meta.lights.implemented;

import com.github.severinnitsche.essentials.abstracted.ThreeDObject;
import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.essentials.meta.help.ObjectInformation;
import com.github.severinnitsche.utilities.math.MathUtil;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.visual.Color;

public class Light implements com.github.severinnitsche.essentials.meta.lights.abstracted.Light {

  public static final double SURFACE_DISTANCE = .01;
  public static final double ZERO             = .1;

  protected Point position = null;
  protected ThreeDObject[] objects  = null;
  protected double strength;
  protected Color color;
  
  public Light(Point position, ThreeDObject[] objects, double strength, Color color) {
    this.position = position;
    this.objects = objects;
    this.strength = strength;
    this.color = color;
  }
  
  public double hasLightLevel(Point pos) {
    //Guard clauses
    if (position == null) throw new IllegalStateException("Position has to be not null");
    if (position.dimensions() != 3) throw new IllegalStateException("Position has to have 3 and only three indicies");
    if (pos == null) throw new IllegalArgumentException("Pos(ition) has to be not null");
    if (pos.dimensions() != 3) throw new IllegalArgumentException("Pos(ition) has to have 3 and only three indicies");
    if (objects == null) throw new IllegalStateException("No objects defined");

    //simplest case
    if (objects.length == 0) return 1;

    //Light -> Point
    Vector dir = pos.subtract(position);
    //Point -> Light
    Vector rev = position.subtract(pos);

    //Normal at pos
    Vector normal = null;

    //find distance to closest object
    Ray check  = new Ray(position, dir);
    double record = Double.POSITIVE_INFINITY;
    for (ThreeDObject object : objects) {
      ObjectInformation temporaryData = object.collide(check);
      if(temporaryData==null) continue;
      if (temporaryData.distance < record && temporaryData.distance > SURFACE_DISTANCE) {
        record = temporaryData.distance;
        //get normal
        try {
          normal = temporaryData.normal;//object.getNormalAt(pos);
        } catch(IllegalArgumentException e) {
          e.printStackTrace();
        }
      }
    }
    //Guard clause
    if(normal == null) throw new IllegalStateException("Point does not lie on object!");

    //basic directional shading
    double ds = normal.dot(rev.normalize());
    ds = MathUtil.clamp(ds, 0, 1);

    //squared fallof
    double sf = strength / dir.squaredMagnitude();
    sf = MathUtil.clamp(sf, 0, 1);

    //combined return
    double ret = ds * sf;

    if (Math.abs(record*record - dir.squaredMagnitude())<SURFACE_DISTANCE || dir.squaredMagnitude()<=record*record)
      return ret;
    else
      return 0;
  }

  public boolean isPointLight() {
    return true;
  }
  
  public Vector[] getDirections(int samples) { return MathUtil.getVectorsOnUnitSphere(samples); }
  
  @Override
  public Color getColor(Vector v) {
    return color;
  }
  
  @Override
  public double getStrength() {
    return strength;
  }
  
  @Override
  public Point getPosition() {
    return new Point(position);
  }
  
  @Override
  public Vector getDirTo(Point p) {
    return p.subtract(position);
  }
  
}
