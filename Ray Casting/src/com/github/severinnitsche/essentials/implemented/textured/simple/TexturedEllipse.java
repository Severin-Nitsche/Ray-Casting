package com.github.severinnitsche.essentials.implemented.textured.simple;

import com.github.severinnitsche.essentials.implemented.basic.Ellipse;
import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.essentials.meta.help.ObjectInformation;
import com.github.severinnitsche.utilities.math.MathUtil;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.math.Versor;
import com.github.severinnitsche.utilities.visual.Color;
import com.github.severinnitsche.utilities.visual.Texture;

public class TexturedEllipse extends Ellipse {
  
  protected Versor rotor;
  
  public TexturedEllipse(Texture texture, double roughness, Point pos, Versor r, double a, double b, double c) {
    super(texture, roughness, pos, r, a, b, c);
    rotor = r;
  }
  
  @Override
  public ObjectInformation collide(Ray ray) {
    ObjectInformation info = super.collide(ray);
    
    if(info==null) return null;
    
    Vector normal = rotor.rotate(info.normal);
    
    info.u = normal.getSpherical(1)/ MathUtil.TAU+.5;
    info.v = normal.getSpherical(0)/MathUtil.PI;
    
    info.c = new Color(((Texture)this.color).to(info.u,info.v).getRGB());
    
    return info;
  }
}
