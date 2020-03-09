package com.github.severinnitsche.utilities.visual;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.github.severinnitsche.utilities.math.MathUtil.clamp;

public class Texture extends Color{
  
  private BufferedImage img;
  
  public Texture(String file) throws IOException {
    super(0);
    img = ImageIO.read(new File(file));
  }
  
  public Color to(double u, double v) {
    
    int x = (int)((img.getWidth() - 1) * clamp(u,0,1));
    int y = (int)((img.getHeight() - 1) * clamp(v,0,1));
    
    setRGB(img.getRGB(x,y));
    return this;
  }
}
