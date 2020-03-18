package com.github.severinnitsche.essentials.implemented.textured.lit;

import com.github.severinnitsche.essentials.abstracted.ThreeDObject;
import com.github.severinnitsche.essentials.implemented.textured.simple.TexturedEllipse;
import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.essentials.meta.help.ObjectInformation;
import com.github.severinnitsche.essentials.meta.lights.abstracted.Light;
import com.github.severinnitsche.utilities.math.MathUtil;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.math.Versor;
import com.github.severinnitsche.utilities.math.exceptions.MatrixConversionException;
import com.github.severinnitsche.utilities.visual.Color;
import com.github.severinnitsche.utilities.visual.Texture;

public class LitEllipse extends TexturedEllipse implements Light {
  
  protected com.github.severinnitsche.essentials.meta.lights.implemented.Light light;
  
  public LitEllipse(ThreeDObject[] objects, double strength, Texture texture, double roughness, Point pos, Versor r, double a, double b, double c) {
    super(texture, roughness, pos, r, a, b, c);
    light = new com.github.severinnitsche.essentials.meta.lights.implemented.Light(pos,objects,strength,null);
  }
  
  @Override
  public ObjectInformation collide(Ray ray) {
    if(ray.getPosition().equals(getPosition())) return null;
    return super.collide(ray);
  }
  
  @Override
  public double hasLightLevel(Point p) {
    try {
      if(conversion.translate(p).subtract(conversion.translate(center)).squaredMagnitude()<=1.01) return 1;
      return light.hasLightLevel(p);
    } catch (MatrixConversionException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public Vector[] getDirections(int samples) {
    return light.getDirections(samples);
  }
  
  @Override
  public Color getColor(Vector normal) {
    normal = rotor.rotate(normal.normalize());
    double u = normal.getSpherical(1)/ MathUtil.TAU+.5;
    double v = normal.getSpherical(0)/MathUtil.PI;
    return ((Texture)color).to(u,v);
  }
  
  @Override
  public double getStrength() {
    return light.getStrength();
  }
  
  @Override
  public Point getPosition() {
    return light.getPosition();
  }
  
  @Override
  public Vector getDirTo(Point p) {
    return light.getDirTo(p);
  }
}
