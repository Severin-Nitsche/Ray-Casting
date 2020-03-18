package com.github.severinnitsche.utilities.visual;

import com.github.severinnitsche.utilities.logger.Logger;
import com.github.severinnitsche.utilities.math.Vector;

import java.io.IOException;

import static com.github.severinnitsche.utilities.math.MathUtil.clamp;

public class BumpMap extends Texture {
  
  public BumpMap(String file) throws IOException {
    super(file);
  }
  
  public Vector translate(Vector normal,double strength, double u, double v) {
    Vector mapNormal = get(u,v).multiply(strength);
    /*double theta = normal.getSpherical(0)+mapNormal.getSpherical(0);
    double phi = normal.getSpherical(1)+mapNormal.getSpherical(0);
    return Vector.fromSpherical(theta,phi,1);*/
    return normal.add(mapNormal).normalize();
  }
  
  private Vector get(double u, double v) {
  
    int x = (int)((img.getWidth() - 1) * clamp(u,0,1));
    int y = (int)((img.getHeight() - 1) * clamp(v,0,1));
  
    int height1 = (byte)img.getRGB(x,y);
    int height2 = (byte)img.getRGB((x+1)%img.getWidth(),y);
    int height3 = (byte)img.getRGB(x,(y+1)%img.getHeight());
  
    double mx = (height2 - height1) * img.getWidth();
    double my = (height3 - height1) * img.getHeight();
    
    Vector up = new Vector(mx,my,1).normalize();
  
    return up;
  }
  
}
