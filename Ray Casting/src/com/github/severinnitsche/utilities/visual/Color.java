package com.github.severinnitsche.utilities.visual;

import com.github.severinnitsche.utilities.math.MathUtil;

public class Color {

  public static final byte RED   = 16;
  public static final byte GREEN = 8;
  public static final byte BLUE  = 0;

  private int rgb;

  public Color(int rgb) {
    this.rgb = rgb;
  }
  
  public Color(Color c) { this(c.getRGB()); }

  public Color(byte red, byte green, byte blue) {
    setRGB(red, green, blue);
  }

  public void setRGB(int rgb) {
    this.rgb = rgb;
  }

  public void setRGB(byte red, byte green, byte blue) {
    rgb =              red   & 0xff;
    rgb = (rgb << 8) | green & 0xff;
    rgb = (rgb << 8) | blue  & 0xff;
  }

  public int getRGB() {
    return rgb;
  }

  public int get(byte colorComponent) {
    return (rgb >> colorComponent) & 0xff;
  }

  public Color overlay(Color c, double strength) {
    int red    = get(RED);
    int green  = get(GREEN);
    int blue   = get(BLUE);

    int cRed   = c.get(RED);
    int cGreen = c.get(GREEN);
    int cBlue  = c.get(BLUE);

    int nRed   = (int) ((1d - strength) * red + strength * cRed);
    int nGreen = (int) ((1d - strength) * green + strength * cGreen);
    int nBlue  = (int) ((1d - strength) * blue + strength * cBlue);

    setRGB((byte)nRed,(byte)nGreen,(byte)nBlue);
    return this;
  }
  
  public Color max(Color c) {
    int red    = get(RED);
    int green  = get(GREEN);
    int blue   = get(BLUE);
    
    int cRed   = c.get(RED);
    int cGreen = c.get(GREEN);
    int cBlue  = c.get(BLUE);
    
    int nRed   = cRed>red?cRed:red;
    int nGreen = cGreen>green?cGreen:green;
    int nBlue  = cBlue>blue?cBlue:blue;
    
    setRGB((byte)nRed,(byte)nGreen,(byte)nBlue);
    return this;
  }

  public Color mult(double d) {
    if(0>d||d>1) throw new IllegalArgumentException("Argument out of range: expected value between 0 and 1");
    setRGB((byte)(get(RED) * d),(byte)(get(GREEN) * d),(byte)(get(BLUE) * d));
    return this;
  }
  
  public Color multS(double d) {
    return mult(MathUtil.clamp(d,0,1));
  }
  
  public Color multU(double d) {
    //if(0>d||d>1) throw new IllegalArgumentException("Argument out of range: expected value between 0 and 1");
    setRGB((byte)(MathUtil.clamp(get(RED) * d,Byte.MIN_VALUE,Byte.MAX_VALUE)),(byte)MathUtil.clamp(get(GREEN) * d,Byte.MIN_VALUE,Byte.MAX_VALUE),(byte)MathUtil.clamp(get(BLUE) * d,Byte.MIN_VALUE,Byte.MAX_VALUE));
    return this;
  }

  public Color add(Color c) {
    int red    = get(RED);
    int green  = get(GREEN);
    int blue   = get(BLUE);

    int cRed   = c.get(RED);
    int cGreen = c.get(GREEN);
    int cBlue  = c.get(BLUE);
    setRGB((byte)(MathUtil.clamp(red + cRed, 0, 255)),(byte)(MathUtil.clamp(green + cGreen, 0, 255)),(byte)(MathUtil.clamp(blue + cBlue, 0, 255)));
    return this;
  }

  public Color limit(Color c) {
    int red = MathUtil.min(c.get(RED),get(RED));
    int green = MathUtil.min(c.get(GREEN),get(GREEN));
    int blue = MathUtil.min(c.get(BLUE),get(BLUE));

    setRGB((byte)red,(byte)green,(byte)blue);
    return this;
  }

  public static Color black() {
    return new Color(0);
  }

  @Override
  public String toString() {
    return ""+rgb;
  }

  @Override
  public Color clone() {
    return new Color(rgb);
  }

  @Override
  public boolean equals(Object o) {
    if(o instanceof Color) {
      return ((Color)o).rgb == rgb;
    }
    return false;
  }

}
