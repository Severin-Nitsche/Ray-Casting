package com.github.severinnitsche.utilities.visual;

import com.github.severinnitsche.utilities.math.MathUtil;
import com.github.severinnitsche.utilities.math.Vector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.github.severinnitsche.utilities.math.MathUtil.clamp;

public class Texture extends Color{
  
  protected BufferedImage img;
  
  public Texture(String file) throws IOException {
    super(0);
    img = ImageIO.read(new File(file));
  }
  
  protected Texture() {
    super(0);
  }
  
  public static double[] uvFromNormal(Vector normal) {
    double tu = normal.getSpherical(1)/ MathUtil.TAU+.5;
    double tv = normal.getSpherical(0)/MathUtil.PI;
    double[] uv = {tu,tv};
    return uv;
  }
  
  public Color to(double u, double v) {
    
    int x = (int)((img.getWidth() - 1) * clamp(u,0,1));
    int y = (int)((img.getHeight() - 1) * clamp(v,0,1));
    
    setRGB(img.getRGB(x,y));
    return this;
  }
}
