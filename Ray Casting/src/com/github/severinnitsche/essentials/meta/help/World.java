package com.github.severinnitsche.essentials.meta.help;

import com.github.severinnitsche.essentials.abstracted.ThreeDObject;
import com.github.severinnitsche.essentials.meta.Light;
import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.utilities.math.MathUtil;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.visual.Color;

public class World {
  
  public static final double SURFACE_DISTANCE = .01;
  
  private ThreeDObject[] objects;
  private Light[] lights;

  public World(ThreeDObject[] objects, Light[] lights) {
    this.objects = objects;
    this.lights = lights;
  }

  private LightInfo lightFor(Point p) {
    if(lights==null) return null;
    double[] ret = new double[lights.length];
    for(int i=0; i<lights.length; i++) {
      ret[i] = lights[i].hasLightLevel(p);
    }
    LightInfo info = new LightInfo(ret,lights);
    return info;
  }

  private ObjectInformation infoFor(Ray ray) {
    double record = Double.POSITIVE_INFINITY;
    ObjectInformation info = null;
    for (ThreeDObject object : objects) {
      ObjectInformation temporaryData = object.collide(ray);
      if(temporaryData==null) continue;
      if (temporaryData.distance < record && temporaryData.distance > SURFACE_DISTANCE) {
        record = temporaryData.distance;
        info = temporaryData;
      }
    }
    return info;
  }

  public Color colorFor(Ray ray, int depth) {
    //TODO: implement roughness
    Color[] colors = new Color[depth];
    LightInfo[] lighting = new LightInfo[depth];
    ThreeDObject[] objects = new ThreeDObject[depth];
    for(int d=0; d<depth; d++) {
      ObjectInformation info = infoFor(ray);
      if(info==null) continue;
      Vector normal = info.normal;
      Vector reflection = ray.getDirection().reject(normal).subtract(ray.getDirection().project(normal));
      ray = new Ray(info.position, reflection);
      colors[d] = new Color(info.c.getRGB());
      lighting[d] = lightFor(info.position);
      objects[d] = info.o;
    }
    Color c = null;
    for(int i=depth-1; i>=0; i--) {
      if(colors[i]==null) continue;
      if(lighting[i]==null) continue;
      Color temp = lighting[i].litColor(colors[i]);
      if(c==null) c=temp;
      else c = temp.add(c.mult(objects[i+1].reflectance()).mult(MathUtil.map(objects[i].roughness(),0,1,1,0)));
    }
    return c==null?Color.black():c;
  }
}
