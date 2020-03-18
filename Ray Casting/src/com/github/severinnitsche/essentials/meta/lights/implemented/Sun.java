package com.github.severinnitsche.essentials.meta.lights.implemented;

import com.github.severinnitsche.essentials.abstracted.ThreeDObject;
import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.essentials.meta.help.ObjectInformation;
import com.github.severinnitsche.utilities.math.MathUtil;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.visual.Color;

public class Sun implements com.github.severinnitsche.essentials.meta.lights.abstracted.Light {
  
  protected Vector direction;
  protected ThreeDObject[] objects;
  protected Color color;
  
  public Sun(Vector direction, ThreeDObject[] objects, Color color) {
    this.direction = direction;
    this.objects = objects;
    this.color = color;
  }
  
  @Override
  public double hasLightLevel(Point pos) {
    //Guard clauses
    assert direction != null : "Direction is missing";
    assert direction.dimensions() == 3 : "Expected Direction to be 3-Dimensional";
    assert pos != null : "Expected pos to exist";
    assert pos.dimensions() == 3 : "Expected pos to be 3-Dimensional";
    assert objects != null : "Expected Models to exist";
    
    //simplest case
    if (objects.length == 0) return 1;
    
    //Light -> Point
    Vector dir = new Vector(direction);
    //Point -> Light
    Vector rev = new Vector(direction).multiply(-1);
    
    //Normal at pos
    Vector normal = null;
    
    //find distance to closest object
    //subtract dir to set back so the correct normal is obtained for eg. spheres
    Ray check  = new Ray(pos.subtractN(dir), dir);
    double record = Double.POSITIVE_INFINITY;
    Point position = null;
    for (ThreeDObject object : objects) {
      ObjectInformation temporaryData = object.collide(check);
      if(temporaryData==null) continue;
      if (temporaryData.distance < record) {
        record = temporaryData.distance;
        //record position to compare to pos (shadow or not)
        position = temporaryData.position;
        //get normal
        normal = temporaryData.normal;
      }
    }
    //Guard clause
    assert normal != null : "Point must need to lie in an Object in order to retain a normal";
    
    //basic directional shading
    double ds = normal.dot(rev.normalize());
    ds = MathUtil.clamp(ds, 0, 1);
    
    if (pos.subtract(position).squaredMagnitude()<SURFACE_DISTANCE)
      return ds;
    else
      return 0;
  }
  
  @Override
  public Vector[] getDirections(int samples) { return MathUtil.getVectorsOnUnitSphere(samples); }
  
  @Override
  public Color getColor(Vector v) {
    return color;
  }
  
  @Override
  public double getStrength() {
    return Double.POSITIVE_INFINITY;
  }
  
  @Override
  public Point getPosition() {
    return new Point(0,0,0);
  }
  
  @Override
  public Vector getDirTo(Point p) {
    return new Vector(direction);
  }
  
}
