package com.github.severinnitsche.essentials.implemented.textured.bumped;

import com.github.severinnitsche.essentials.abstracted.ThreeDObject;
import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.essentials.meta.help.ObjectInformation;
import com.github.severinnitsche.utilities.logger.Logger;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.math.Versor;
import com.github.severinnitsche.utilities.visual.BumpMap;

public class Container implements ThreeDObject {
  
  protected ThreeDObject object;
  protected BumpMap bump;
  protected Versor rotor;
  protected double strength;
  
  public Container(ThreeDObject object, BumpMap bump, Versor rotor, double strength) {
    this.object = object;
    this.bump = bump;
    this.rotor = rotor;
    this.strength = strength;
  }
  
  @Override
  public ObjectInformation collide(Ray ray) {
    ObjectInformation info = object.collide(ray);
    if(info == null) return null;
    info.normal = bump.translate(info.normal,strength,info.u,info.v);
    return info;
  }
  
  @Override
  public double roughness() {
    return object.roughness();
  }
  
  @Override
  @Deprecated
  public Vector getNormalAt(Point p) {
    Logger.LOGGER.warn("It is Discouraged to use: getNormalAt(Point)");
    return object.getNormalAt(p);
  }
  
  @Override
  public double reflectance() {
    return object.reflectance();
  }
}
